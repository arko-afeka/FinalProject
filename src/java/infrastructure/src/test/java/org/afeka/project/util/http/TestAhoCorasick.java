package org.afeka.project.util.http;

import org.afeka.project.algorithm.ahocorasick.Builder;
import org.afeka.project.model.algorithm.ahocorasick.Token;
import org.afeka.project.model.algorithm.ahocorasick.TrieConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.hasItems;

@RunWith(JUnit4.class)
public class TestAhoCorasick {
    @Test
    public void testSimple() {
        Collection<String> keywords = Arrays.asList(
                "he", "she", "his", "hers"
        );

        Builder<String> builder = new Builder<>(new TrieConfig<>(true, true, "default"));
        keywords.forEach(key -> builder.addKeyword(key, key));

        var trie = builder.build();


        assertThat(trie.getTokens("is she dead, on hers fault"), hasItems(
                new Token<>("is ", 0, 3, "default"),
                new Token<>("she", 3, 6, "she"),
                new Token<>(" dead, on ", 6, 16, "default"),
                new Token<>("he", 4, 6, "he"),
                new Token<>("he", 16, 18, "he"),
                new Token<>("hers", 16, 20, "hers"),
                new Token<>(" fault", 20, 26, "default")
        ));
    }
}
