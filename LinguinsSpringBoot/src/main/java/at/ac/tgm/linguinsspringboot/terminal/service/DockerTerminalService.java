package at.ac.tgm.linguinsspringboot.terminal.service;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class DockerTerminalService {

    private final RestTemplate restTemplate;

    public DockerTerminalService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    /**
     * Kann verwendet werden, um mit der Docker API zu kommunizieren
     * wenn Sie erweiterte Features benötigen
     */
    public void executeCommandInContainer(String containerId, String command) {
        // Hier könnte die Docker API aufgerufen werden
        // z.B. über localhost:2375/containers/{id}/exec
    }
}