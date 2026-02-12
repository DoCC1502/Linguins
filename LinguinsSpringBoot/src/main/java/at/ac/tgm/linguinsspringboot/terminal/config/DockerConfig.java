package at.ac.tgm.linguinsspringboot.terminal.config;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

@Configuration
public class DockerConfig {

    @Bean
    @Lazy
    public DockerClient dockerClient() {
        DefaultDockerClientConfig config = DefaultDockerClientConfig.createDefaultConfigBuilder()
                .withDockerHost("tcp://localhost:2375")
                .withDockerTlsVerify(false)
                .build();

        // Nutzt den HttpClient5 aus der neuen Dependency
        com.github.dockerjava.transport.DockerHttpClient httpClient =
                new com.github.dockerjava.httpclient5.ApacheDockerHttpClient.Builder()
                        .dockerHost(config.getDockerHost())
                        .sslConfig(config.getSSLConfig())
                        .build();

        try {
            DockerClient client = DockerClientBuilder.getInstance(config)
                    .withDockerHttpClient(httpClient)
                    .build();

            client.pingCmd().exec();
            System.out.println("✅ Docker Verbindung steht!");
            return client;

        } catch (Exception e) {
            System.err.println("⚠️ Docker nicht erreichbar, aktiviere Safe-Mock-Mode...");
            return createSafeMock();
        }
    }

    private DockerClient createSafeMock() {
        DockerClient mock = org.mockito.Mockito.mock(DockerClient.class, org.mockito.Mockito.RETURNS_DEEP_STUBS);

        // Verhindert NullPointer in DockerContainerService.findUbuntuContainer()
        org.mockito.Mockito.when(mock.listContainersCmd().withShowAll(org.mockito.Mockito.anyBoolean()).exec())
                .thenReturn(java.util.Collections.emptyList());

        return mock;
    }
}