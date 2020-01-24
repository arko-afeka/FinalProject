package org.afeka.project.model.algorithm.ahocorasick;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import java.util.*;

public class TrieNode<V> {
  private Map<Character, TrieNode<V>> childNodes;
  private TrieNode<V> failureNode;
  private Set<Emit<V>> emits;

  public TrieNode() {
    this.childNodes = Maps.newHashMap();
    this.emits = Sets.newHashSet();
  }

  public Map<Character, TrieNode<V>> getChildNodes() {
    return childNodes;
  }

  public Optional<TrieNode<V>> getNextNode(char c) {
    return Optional.ofNullable(childNodes.get(c));
  }

  public TrieNode<V> getFailureNode() {
    return failureNode;
  }

  public Set<Emit<V>> getEmits() {
    return emits;
  }

  public void addEmit(Emit<V> emit) {
    this.emits.add(emit);
  }

  public void addEmits(Collection<Emit<V>> emits) {
    this.emits.addAll(emits);
  }

  public void setFailureNode(TrieNode<V> failureNode) {
    this.failureNode = failureNode;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    TrieNode<?> trieNode = (TrieNode<?>) o;
    return childNodes.equals(trieNode.childNodes)
        && failureNode.equals(trieNode.failureNode)
        && emits.equals(trieNode.emits);
  }

  @Override
  public int hashCode() {
    return Objects.hash(childNodes, failureNode, emits);
  }
}
