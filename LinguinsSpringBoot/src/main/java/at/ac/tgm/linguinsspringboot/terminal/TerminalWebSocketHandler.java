package at.ac.tgm.linguinsspringboot.terminal;

import at.ac.tgm.linguinsspringboot.terminal.service.DockerContainerService;
import com.github.dockerjava.api.async.ResultCallback;
import com.github.dockerjava.api.model.Frame;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class TerminalWebSocketHandler extends TextWebSocketHandler {

    private final DockerContainerService dockerService;

    // Verwaltung der Streams pro Session
    private final Map<String, ResultCallback.Adapter<Frame>> dockerCallbacks = new ConcurrentHashMap<>();
    private final Map<String, PipedOutputStream> stdinStreams = new ConcurrentHashMap<>();

    public TerminalWebSocketHandler(DockerContainerService dockerService) {
        this.dockerService = dockerService;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        // AUTOMATISIERUNG: Holt den Container, erstellt ihn wenn nötig oder startet ihn.
        // Kein manuelles "docker start" mehr nötig!
        String containerId = dockerService.getOrCreateContainer();

        // 1. Interaktive Exec-Instanz erstellen (/bin/bash)
        var execResponse = dockerService.createInteractiveShell(containerId);

        // 2. Piped Streams aufbauen:
        // WebSocket-Input -> PipedOutputStream -> PipedInputStream -> Docker Stdin
        PipedOutputStream pos = new PipedOutputStream();
        PipedInputStream pis = new PipedInputStream(pos);
        stdinStreams.put(session.getId(), pos);

        // 3. Callback für den Output vom Docker-Container
        var callback = new ResultCallback.Adapter<Frame>() {
            @Override
            public void onNext(Frame frame) {
                try {
                    if (session.isOpen()) {
                        // Den Payload (Shell Output) direkt an das Web-Frontend senden
                        session.sendMessage(new TextMessage(frame.getPayload()));
                    }
                } catch (IOException e) {
                    System.err.println("WS-Sende-Fehler: " + e.getMessage());
                }
            }
        };

        // 4. Den Exec-Stream starten
        dockerService.getDockerClient()
                .execStartCmd(execResponse.getId())
                .withTty(true)
                .withStdIn(pis)
                .exec(callback);

        dockerCallbacks.put(session.getId(), callback);

        System.out.println("✅ Terminal-Session verbunden: " + session.getId());
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
        PipedOutputStream os = stdinStreams.get(session.getId());
        if (os != null) {
            // Empfange Tastendrücke vom Frontend und leite sie an Docker weiter
            os.write(message.getPayload().getBytes());
            os.flush();
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        // Ressourcen sauber schließen, sonst entstehen Zombie-Prozesse im Container
        PipedOutputStream os = stdinStreams.remove(session.getId());
        if (os != null) {
            os.close();
        }

        ResultCallback.Adapter<Frame> callback = dockerCallbacks.remove(session.getId());
        if (callback != null) {
            callback.close();
        }

        System.out.println("🔌 Terminal-Session beendet: " + session.getId());
    }
}