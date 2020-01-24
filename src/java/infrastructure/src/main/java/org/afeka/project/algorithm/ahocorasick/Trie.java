package org.afeka.project.algorithm.ahocorasick;

import com.google.common.collect.Queues;
import com.google.common.collect.Sets;
import org.afeka.project.model.algorithm.ahocorasick.Emit;
import org.afeka.project.model.algorithm.ahocorasick.TrieConfig;
import org.afeka.project.model.algorithm.ahocorasick.TrieNode;

import java.util.Collection;
import java.util.Queue;
import java.util.Set;

public class Trie<V> {
    private TrieNode<V> rootState;
    private TrieConfig<V> config;

    Trie(TrieConfig<V> config) {
        this.rootState = new TrieNode<>();
        this.config = config;
    }

    private void addStates(String keyword, V type) {
        TrieNode<V> currentState = rootState;

        if (this.config.isIgnoreCase()) {
            keyword = keyword.toLowerCase();
        }

        for (Character c : keyword.toCharArray()) {
            currentState = currentState.getChildNodes().computeIfAbsent(c, (transition) -> new TrieNode<>());
        }

        currentState.addEmit(new Emit<>(keyword, type));
    }

    void addKeyword(String keyword, V type) {
        addStates(keyword, type);
    }

    void fillFailureLinks() {
        this.rootState.getChildNodes().forEach((transition, node) -> {
            node.setFailureNode(this.rootState);
        });

        Queue<TrieNode<V>> queue = Queues.newArrayDeque(this.rootState.getChildNodes().values());

        while (!queue.isEmpty()) {
            var currentNode = queue.remove();

            currentNode.getChildNodes().forEach((transition, node) -> {
                var failureNode = currentNode.getFailureNode();

                while (failureNode.getNextNode(transition).isEmpty()) {
                    failureNode = failureNode.getFailureNode();
                }

                failureNode = failureNode.getNextNode(transition).get();

                node.setFailureNode(failureNode);
                node.addEmits(failureNode.getEmits());
            });

            queue.addAll(currentNode.getChildNodes().values());
        }
    }

    public Collection<TrieNode<V>> toBFS() {
        Set<TrieNode<V>> result = Sets.newHashSet();
        Queue<TrieNode<V>> queue = Queues.newArrayDeque();
        queue.add(this.rootState);
        result.add(this.rootState);


        while (!queue.isEmpty()) {
            var node = queue.remove();

            result.addAll(node.getChildNodes().values());
        }

        return result;
    }
}
