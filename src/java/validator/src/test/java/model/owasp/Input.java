package model.owasp;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;
import java.util.Objects;

public class Input {
  @JsonProperty("dest_addr")
  private String destAddr;

  private Map<String, String> headers;
  private String method;
  private Integer port;
  private String uri;
  private String version;
  private Data data;

  @JsonProperty("stop_magic")
  private boolean stopMagic;

  public Input() {}

  public Input(
      String destAddr,
      Map<String, String> headers,
      String method,
      Integer port,
      String uri,
      String version,
      Data data) {
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
    if (!headers.containsKey("Content-Type")) {
      headers.put("Content-Type", "application/x-www-form-urlencoded");
    }
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
    return Objects.requireNonNullElse(data, new Data("")).getData();
  }

  public String getVersion() {
    return version;
  }

  public boolean isStopMagic() {
    return stopMagic;
  }

  @Override
  public String toString() {
    return "Input{"
        + "destAddr='"
        + destAddr
        + '\''
        + ", headers="
        + headers
        + ", method='"
        + method
        + '\''
        + ", port="
        + port
        + ", uri='"
        + uri
        + '\''
        + ", version='"
        + version
        + '\''
        + ", data="
        + data
        + ", stopMagic="
        + stopMagic
        + '}';
  }
}
