import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.afeka.project.algorithm.ahocorasick.Builder;
import org.afeka.project.algorithm.ahocorasick.Trie;
import org.afeka.project.model.algorithm.ahocorasick.Token;
import org.afeka.project.model.algorithm.ahocorasick.TrieConfig;
import org.afeka.project.validation.plugin.sqli.libinjection.model.TokenType;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.IOException;
import java.util.*;
import java.util.stream.IntStream;

@RunWith(JUnit4.class)
public class SQLITokenizer {

  private Trie<TokenType> trie;

  public SQLITokenizer() {}

  private static class StoredToken {
    private String token;
    private TokenType type;

    public StoredToken() {}

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
            new TypeReference<List<StoredToken>>() {})
        .stream()
        .filter(x -> !x.getType().equals(TokenType.TYPE_FINGERPRINT))
        .forEach(x -> tokensMap.put(x.getToken().toLowerCase(), x.getType()));

    IntStream.range(0, 10).forEach(x -> tokensMap.put(Integer.toString(x), TokenType.TYPE_NUMBER));
    trie = new Builder<>(tokensMap, new TrieConfig<>(true, true, TokenType.TYPE_STRING)).build();
  }

  private Collection<Token<TokenType>> prepareResults(Collection<Token<TokenType>> tokens) {
    return tokens.stream().filter(x -> !x.equals())
  }

  @Test
  public void test() {
    final String text = "select 1,'''',2;";

    System.out.println(trie.getTokens(text));
  }
}
