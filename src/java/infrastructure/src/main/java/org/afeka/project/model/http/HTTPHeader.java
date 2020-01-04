package org.afeka.project.model.http;

import java.util.Arrays;
import java.util.Optional;

public enum HTTPHeader {
  USER_AGENT("user-agent"),
  HOST("host"),
  ;
  private String name;

  HTTPHeader(String name) {
    this.name = name;
  }

  public static Optional<HTTPHeader> forName(String name) {
    return Arrays.stream(HTTPHeader.values())
        .filter(storedName -> storedName.getName().equals(name.toLowerCase()))
        .findFirst();
  }

  public String getName() {
    return name;
  }
}
