package org.afeka.project.model.algorithm.ahocorasick;

import java.util.Optional;

public class TrieConfig<V> {
  private boolean isIgnoreCase;
  private V defaultType;

  public TrieConfig(boolean isIgnoreCase, V defaultType) {
    this.isIgnoreCase = isIgnoreCase;
    this.defaultType = defaultType;
  }

  public TrieConfig() {
    this(false, null);
  }

  public boolean isIgnoreCase() {
    return isIgnoreCase;
  }


  public Optional<V> getDefaultType() {
    return Optional.ofNullable(defaultType);
  }
}
