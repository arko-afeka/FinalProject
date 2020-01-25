package org.afeka.project.model.algorithm.ahocorasick;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import java.util.*;

public class TrieNode<V> {
  private Map<Character, TrieNode<V>> childNodes;
  private TrieNode<V> failureNode;
  private TrieNode<V> outputNode;
  private Emit<V> emit;

  public TrieNode() {
    this.childNodes = Maps.newHashMap();
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

  public Optional<Emit<V>> getEmit() {
    return Optional.ofNullable(emit);
  }

  public void setEmit(Emit<V> emit) {
    this.emit = emit;
  }

  public void setFailureNode(TrieNode<V> failureNode) {
    this.failureNode = failureNode;
  }

  public Optional<TrieNode<V>> getOutputNode() {
    return Optional.ofNullable(outputNode);
  }

  public void addState(char transition, TrieNode<V> node) {
    this.childNodes.put(transition, node);
  }

  public void setOutputNode(TrieNode<V> outputNode) {
    this.outputNode = outputNode;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    TrieNode<?> trieNode = (TrieNode<?>) o;
    return childNodes.equals(trieNode.childNodes)
        && failureNode.equals(trieNode.failureNode)
        && emit.equals(trieNode.emit);
  }
}
