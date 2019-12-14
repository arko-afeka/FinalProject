package org.afeka.project.model.http;

public interface HTTPHeaderLine {
    HTTPMessageType getType();

    int getHTTPMajorVersion();
    int getHTTPMinorVersion();
}
