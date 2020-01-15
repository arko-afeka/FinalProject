package org.afeka.project.validation;

public interface ValidationModuleConfiguration {
  <T> T getValue(String key);
}
