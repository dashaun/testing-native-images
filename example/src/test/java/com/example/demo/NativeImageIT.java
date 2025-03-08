package com.example.demo;

import org.apache.maven.shared.invoker.DefaultInvocationRequest;
import org.apache.maven.shared.invoker.DefaultInvoker;
import org.apache.maven.shared.invoker.InvocationResult;
import org.apache.maven.shared.invoker.MavenInvocationException;
import org.junit.jupiter.api.BeforeAll;
import org.testcontainers.DockerClientFactory;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.utility.LazyFuture;

import java.io.File;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.Future;

public class NativeImageIT extends AbstractAppTests {
    private static int port;

    @Override
    int getPort() {
        return port;
    }

    private static final Future<String> IMAGE_FUTURE = new LazyFuture<>() {
        @Override
        protected String resolve() {
            // Find project's root dir
            File cwd;
            cwd = new File(".");
            while (!new File(cwd, "mvnw").isFile()) {
                cwd = cwd.getParentFile();
            }

            String imageName = "dashaun/local-test:latest";

            Properties properties = new Properties();
            properties.put("spring-boot.build-image.imageName", imageName);

            var request = new DefaultInvocationRequest()
                    .addShellEnvironment("DOCKER_HOST", DockerClientFactory.instance().getTransportConfig().getDockerHost().toString())
                    .setPomFile(new File(cwd, "pom.xml"))
                    .setGoals(List.of("spring-boot:build-image"))
                    .setMavenExecutable(new File(cwd, "mvnw"))
                    .setProfiles(List.of("native"))
                    .setProperties(properties);

            InvocationResult invocationResult;
            try {
                invocationResult = new DefaultInvoker().execute(request);
            } catch (MavenInvocationException e) {
                throw new RuntimeException(e);
            }

            if (invocationResult.getExitCode() != 0) {
                throw new RuntimeException(invocationResult.getExecutionException());
            }

            return imageName;

        }
    };

    @Container
    static final GenericContainer<?> APP = new GenericContainer<>(IMAGE_FUTURE)
            .withExposedPorts(8080)
            .withNetworkAliases("app")
            .waitingFor(Wait.forHttp("/actuator/health"))
            .withStartupTimeout(Duration.of(600, ChronoUnit.SECONDS));

    @BeforeAll
    static void setUp() {
        APP.start();
        port = APP.getFirstMappedPort();
    }
}
