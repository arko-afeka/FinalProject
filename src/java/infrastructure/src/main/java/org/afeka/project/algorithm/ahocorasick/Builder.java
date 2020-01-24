package org.afeka.project.algorithm.ahocorasick;

import org.afeka.project.model.algorithm.ahocorasick.TrieConfig;

import java.util.Collections;
import java.util.Map;

public class Builder<V> {
  private Map<String, V> tokens;
  private Trie<V> trie;

  public Builder(Map<String, V> tokens, TrieConfig<V> config) {
    this.tokens = tokens;
    this.trie = new Trie<>(config);
    tokens.forEach(this.trie::addKeyword);
  }

  public Builder(TrieConfig<V> config) {
    this(Collections.emptyMap(), config);
  }

  public Builder() {
    this(Collections.emptyMap(), new TrieConfig<>());
  }

  public void addKeyword(String key, V type) {
    this.trie.addKeyword(key, type);
  }

  public Trie<V> build() {
    this.trie.fillFailureLinks();

    return this.trie;
  }
}
