package ru.practicum.config;

import net.devh.boot.grpc.server.config.GrpcServerProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GrpcServerConfig {

    public GrpcServerConfig(GrpcServerProperties grpcServerProperties) {
        grpcServerProperties.setPort(9097);
    }
}
