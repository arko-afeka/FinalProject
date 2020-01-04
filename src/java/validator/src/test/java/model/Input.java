package model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public class Input {
    @JsonProperty("dest_addr")
    private String destAddr;
    private Map<String, String> headers;
    private String method;
    private Integer port;
    private String uri;
    private String data;
    private String version;

    public Input() {

    }

    public Input(String destAddr, Map<String, String> headers, String method, Integer port, String uri, String data, String version) {
        this.destAddr = destAddr;
        this.headers = headers;
        this.method = method;
        this.port = port;
        this.uri = uri;
        this.data = data;
        this.version = version;
    }

    public String getDestAddr() {
        return destAddr;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public String getMethod() {
        return method;
    }

    public Integer getPort() {
        return port;
    }

    public String getUri() {
        return uri;
    }

    public String getData() {
        return data;
    }

    public String getVersion() {
        return version;
    }
}
