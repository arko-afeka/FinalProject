package org.afeka.project.model.http;

import java.util.Map;

public class HTTPMessage {
    private HTTPHeaderLine firstLine;

    private Map<String, String> headers;
    private String body;

    public HTTPMessage(HTTPHeaderLine firstLine, Map<String, String> headers, String body) {
        this.firstLine = firstLine;
        this.headers = headers;
        this.body = body;
    }

    public HTTPHeaderLine getHeaderLine() {
        return firstLine;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public String getBody() {
        return body;
    }
}
