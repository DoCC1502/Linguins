package at.ac.tgm.linguinsspringboot.terminal.service;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.ExecCreateCmdResponse;
import com.github.dockerjava.api.command.InspectContainerResponse;
import com.github.dockerjava.api.model.Container;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class DockerContainerService {

    private final DockerClient dockerClient;
    private final String CONTAINER_NAME = "web-terminal-ubuntu";
    private final String IMAGE_NAME = "ubuntu:22.04";

    public DockerContainerService(DockerClient dockerClient) {
        this.dockerClient = dockerClient;
    }

    public String getOrCreateContainer() {
        // 1. Prüfen, ob der Container existiert (auch wenn er gestoppt ist)
        List<Container> containers = dockerClient.listContainersCmd()
                .withShowAll(true)
                .withNameFilter(Collections.singletonList(CONTAINER_NAME))
                .exec();

        String containerId;

        if (containers.isEmpty()) {
            // 2. Container existiert nicht -> Erstellen
            System.out.println("🔨 Container existiert nicht. Erstelle " + CONTAINER_NAME + "...");

            // Sicherstellen, dass das Image lokal vorhanden ist
            try {
                dockerClient.pullImageCmd(IMAGE_NAME).start().awaitCompletion();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            containerId = dockerClient.createContainerCmd(IMAGE_NAME)
                    .withName(CONTAINER_NAME)
                    .withTty(true)
                    .withStdInOnce(false)
                    .withStdinOpen(true)
                    .withCmd("tail", "-f", "/dev/null") // Hält ihn am Leben
                    .exec().getId();
        } else {
            containerId = containers.get(0).getId();
        }

        // 3. Prüfen, ob der Container läuft -> Falls nicht, starten
        InspectContainerResponse inspect = dockerClient.inspectContainerCmd(containerId).exec();
        if (!Boolean.TRUE.equals(inspect.getState().getRunning())) {
            System.out.println("🚀 Container gefunden, aber gestoppt. Starte...");
            dockerClient.startContainerCmd(containerId).exec();
        }

        return containerId;
    }

    public ExecCreateCmdResponse createInteractiveShell(String containerId) {
        System.out.println("🔄 Erstelle interaktive Shell für Container: " + containerId);

        return dockerClient.execCreateCmd(containerId)
                .withAttachStdin(true)
                .withAttachStdout(true)
                .withAttachStderr(true)
                .withTty(true)           // Wichtig für interaktive Features wie 'nano' oder 'top'
                .withCmd("/bin/bash")    // Startet die Bash; falls nicht vorhanden, ginge auch "/bin/sh"
                .exec();
    }

    public DockerClient getDockerClient() {
        return this.dockerClient;
    }

    // ... restliche Methoden wie createInteractiveShell ...
}