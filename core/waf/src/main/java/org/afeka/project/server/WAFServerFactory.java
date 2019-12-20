package org.afeka.project.server;

public interface WAFServerFactory {
    WAFServer create(int port);
}
