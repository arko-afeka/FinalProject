package org.afeka.project.server;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

public class WAFServerImpl implements WAFServer {
    private final int port;
    private final Server server;

    @Inject
    public WAFServerImpl(@Assisted int port, WAFService service) {
        this(ServerBuilder.forPort(port), port, service);
    }

    private WAFServerImpl(ServerBuilder<?> builder, int port, WAFService service) {
        this.port = port;
        this.server = builder.addService(service).build();
    }

    @Override
    public void start() throws IOException {
        server.start();

    }

    @Override
    public void stop() throws IOException {
        server.shutdownNow();
    }
}
