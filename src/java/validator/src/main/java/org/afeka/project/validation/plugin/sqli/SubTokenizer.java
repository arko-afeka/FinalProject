package org.afeka.project.validation.plugin.sqli;

import org.ahocorasick.trie.Token;

import java.util.Collection;

public interface SubTokenizer {
    Collection<Token> tokenize(Collection<Token> tokens);
}
