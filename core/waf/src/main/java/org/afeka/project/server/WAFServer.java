package org.afeka.project.server;

import java.io.IOException;

public interface WAFServer {
    void start() throws IOException;
    void stop() throws IOException;
}
