package org.afeka.project.model.algorithm.ahocorasick;

import java.util.Optional;

public class TrieConfig<V> {
  private boolean isIgnoreCase;
  private boolean ignoreWhiteSpace;
  private V defaultType;

  public TrieConfig(boolean isIgnoreCase, boolean ignoreWhiteSpace, V defaultType) {
    this.isIgnoreCase = isIgnoreCase;
    this.defaultType = defaultType;
    this.ignoreWhiteSpace = ignoreWhiteSpace;
  }

  public TrieConfig() {
    this(false, false, null);
  }

  public boolean isIgnoreWhiteSpace() {
    return ignoreWhiteSpace;
  }

  public boolean isIgnoreCase() {
    return isIgnoreCase;
  }

  public Optional<V> getDefaultType() {
    return Optional.ofNullable(defaultType);
  }
}
