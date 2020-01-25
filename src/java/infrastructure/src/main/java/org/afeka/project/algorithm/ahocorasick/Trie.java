package org.afeka.project.algorithm.ahocorasick;

import com.google.common.collect.Lists;
import com.google.common.collect.Queues;
import com.google.common.collect.Sets;
import com.google.inject.internal.cglib.core.internal.$CustomizerRegistry;
import org.afeka.project.model.algorithm.ahocorasick.Emit;
import org.afeka.project.model.algorithm.ahocorasick.Token;
import org.afeka.project.model.algorithm.ahocorasick.TrieConfig;
import org.afeka.project.model.algorithm.ahocorasick.TrieNode;

import java.nio.CharBuffer;
import java.util.*;

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

        currentState.setEmit(new Emit<>(keyword, type));
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

                while (!Objects.isNull(failureNode) && failureNode.getNextNode(transition).isEmpty()) {
                    failureNode = failureNode.getFailureNode();
                }

                failureNode = Objects.requireNonNullElse(failureNode, rootState).getNextNode(transition).orElse(rootState);

                node.setFailureNode(failureNode);
                if (failureNode.getEmit().isPresent()) {
                    node.setOutputNode(failureNode);
                }
            });

            queue.addAll(currentNode.getChildNodes().values());
        }

        rootState.setFailureNode(rootState);
    }

    public Collection<Token<V>> getTokens(String input) {
        List<Token<V>> tokens = parseText(input);

        int processed = 0;

        if (config.getDefaultType().isPresent()) {
            for (int i = 0; i < tokens.size(); i++) {
                Token<V> currentTok = tokens.get(i);

                if (processed < currentTok.getBegin()) {
                    tokens.add(i, new Token<>(input.substring(processed, currentTok.getBegin()), processed, currentTok.getBegin(), config.getDefaultType().get()));
                }

                processed = currentTok.getEnd();
            }

            if (input.length() > processed) {
                tokens.add(new Token<>(input.substring(processed),
                        processed, input.length(), config.getDefaultType().get()));
            }
        }
        return tokens;
    }

    private List<Token<V>> parseText(String input) {
        if (config.isIgnoreCase()) {
            input = input.toLowerCase();
        }

        TrieNode<V> currentState = this.rootState;

        CharBuffer buffer = CharBuffer.wrap(input);

        List<Token<V>> result = Lists.newArrayList();
        while (buffer.hasRemaining()) {
            char nextChar = buffer.get();
            currentState = currentState.getNextNode(nextChar).orElse(currentState.getFailureNode().getNextNode(nextChar).orElse(this.rootState));

            if (currentState.getEmit().isEmpty()) {
                continue;
            }

            int storedPos = buffer.position();
            Optional<TrieNode<V>> nextState = Optional.of(currentState);
            while (nextState.isPresent()) {
                currentState = nextState.get();
                Emit<V> emit = currentState.getEmit().get();
                int startInd = buffer.position() - emit.getValue().length();
                int endInd = buffer.position();

                buffer.rewind();
                buffer.position(startInd);

                result.add(new Token<>(
                        buffer.subSequence(0, emit.getValue().length()).toString(),
                        startInd,
                        endInd, emit.getType()));

                buffer.position(storedPos);

                nextState = currentState.getOutputNode();
            }
        }

        return result;
    }

    public Collection<TrieNode<V>> toBFS() {
        Set<TrieNode<V>> result = Sets.newLinkedHashSet();
        Queue<TrieNode<V>> queue = Queues.newArrayDeque();
        queue.add(this.rootState);
        result.add(this.rootState);


        while (!queue.isEmpty()) {
            var node = queue.remove();

            result.addAll(node.getChildNodes().values());
            queue.addAll(node.getChildNodes().values());
        }

        return result;
    }
}
