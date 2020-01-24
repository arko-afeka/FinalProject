package org.afeka.project.util.http;

import org.afeka.project.algorithm.ahocorasick.Builder;
import org.afeka.project.model.algorithm.ahocorasick.TrieNode;
import org.ahocorasick.trie.Trie;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

@RunWith(JUnit4.class)
public class TestAhoCorasick {
    @Test
    public void testSimple() {
        Collection<String> keywords = Arrays.asList(
                "vine", "vincent", "cent", "center"
        );

        Builder<String> builder = new Builder<>();
        keywords.forEach(key -> builder.addKeyword(key, key));


        Trie.TrieBuilder trie = Trie.builder();
        trie.addKeywords(keywords);
        trie.build();

        var result = builder.build().toBFS();
        result = result;
    }
}
