package org.afeka.project.validation.plugin.sqli;

import com.google.common.collect.Maps;
import org.afeka.project.algorithm.ahocorasick.Builder;
import org.afeka.project.algorithm.ahocorasick.Trie;
import org.afeka.project.model.algorithm.ahocorasick.Token;
import org.afeka.project.model.algorithm.ahocorasick.TrieConfig;
import org.afeka.project.validation.plugin.sqli.model.TokenType;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class SQLITokenizer {
  private Trie<TokenType> trie;
  private Map<TokenType, SubTokenizer> subTokenizers = Maps.newHashMap();

  public SQLITokenizer(Map<String, TokenType> tokens, Map<TokenType, SubTokenizer> subTokenizers) {
    trie = new Builder<TokenType>(tokens, new TrieConfig<>(true, TokenType.TYPE_STRING)).build();

    this.subTokenizers.putAll(subTokenizers);
  }

  public Collection<Token<TokenType>> tokenize(String input) {
    final Collection<Token<TokenType>> tokens = trie.getTokens(input);

    return tokens
        .parallelStream()
        .map(
            token ->
                new Token<>(
                    token.getToken().trim(), token.getBegin(), token.getEnd(), token.getType()))
        .map(x -> {
            Optional<SubTokenizer> subTokenizer = Optional.ofNullable(subTokenizers.get(x.getType()));

            if (subTokenizer.isPresent()) {
                return subTokenizer.get().tokenize()
            }
        })
        .flatMap(Collection::parallelStream)
        .collect(Collectors.toList());
  }
}
