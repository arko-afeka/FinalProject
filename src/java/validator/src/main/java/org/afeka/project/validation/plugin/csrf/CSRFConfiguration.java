package org.afeka.project.validation.plugin.csrf;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.afeka.project.validation.ValidationModuleConfiguration;

import java.io.IOException;
import java.util.Map;

public class CSRFConfiguration implements ValidationModuleConfiguration {
  private Map<String, Object> configuration;

  public CSRFConfiguration() {
    ObjectMapper mapper = new ObjectMapper();
    try {
      mapper.readValue(
          getClass().getResource("csrf.json"), new TypeReference<Map<String, Object>>() {});
    } catch (IOException e) {
      // TODO: ignored handled
    }
  }

  @Override
  public <T> T getValue(String key) {
    return (T) configuration.get(key);
  }
}
