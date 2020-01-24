import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hankcs.algorithm.AhoCorasickDoubleArrayTrie;
import org.afeka.project.validation.plugin.sqli.libinjection.model.TokenType;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.TreeMap;
import java.util.stream.IntStream;

@RunWith(JUnit4.class)
public class SQLITokenizer {
  private AhoCorasickDoubleArrayTrie<TokenType> trie;

  public SQLITokenizer() {}

  private static class Token {
    private String token;
    private TokenType type;

    public Token() {}

    public String getToken() {
      return token;
    }

    public TokenType getType() {
      return type;
    }
  }

  @Before
  public void setupClass() throws IOException {
    ObjectMapper objectMapper = new ObjectMapper();
    TreeMap<String, TokenType> tokensMap = new TreeMap<>();

    objectMapper
        .readValue(
            Objects.requireNonNull(this.getClass().getResource("sqli/keywords.txt")),
            new TypeReference<List<Token>>() {})
        .stream()
        .filter(x -> !x.getType().equals(TokenType.TYPE_FINGERPRINT))
        .forEach(x -> tokensMap.put(x.getToken().toLowerCase(), x.getType()));

    IntStream.range(0, 10).forEach(x -> tokensMap.put(Integer.toString(x), TokenType.TYPE_NUMBER));
    trie = new AhoCorasickDoubleArrayTrie<>();
    trie.build(tokensMap);
  }

  @Test
  public void test() {
    final String text = "select 1,'''',2;";
    List<AhoCorasickDoubleArrayTrie.Hit<TokenType>> tokens = trie.parseText(text);

    if (tokens.size() == 0) {
      return;
    }

        .forEach(System.out::println);
  }
}
