package sqli;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import org.afeka.project.validation.plugin.sqli.SQLITokenizer;
import org.afeka.project.validation.plugin.sqli.model.TokenType;
import sqli.model.SQLITest;
import org.afeka.project.algorithm.ahocorasick.Builder;
import org.afeka.project.model.algorithm.ahocorasick.TrieConfig;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class SQLITokenizerTest {

  private static SQLITokenizer tokenizer;

  private SQLITest test;

  public SQLITokenizerTest(SQLITest test) {
    this.test = test;
  }

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

  @BeforeClass
  public static void setupClass() throws IOException {
    ObjectMapper objectMapper = new ObjectMapper();
    TreeMap<String, TokenType> tokensMap = new TreeMap<>();

    objectMapper
        .readValue(
            Objects.requireNonNull(SQLITokenizerTest.class.getResource("/sqli/keywords.txt")),
            new TypeReference<List<StoredToken>>() {})
        .stream()
        .filter(x -> !x.getType().equals(TokenType.TYPE_FINGERPRINT))
        .forEach(x -> tokensMap.put(x.getToken().toLowerCase(), x.getType()));

    IntStream.range(0, 10).forEach(x -> tokensMap.put(Integer.toString(x), TokenType.TYPE_NUMBER));
    tokenizer = new SQLITokenizer(tokensMap, Collections.emptyMap());
  }

  @Parameterized.Parameters(name = "{0}")
  public static Collection<SQLITest> getTests() throws URISyntaxException, IOException {
    Collection<File> files =
        Arrays.asList(
            Objects.requireNonNull(
                new File(SQLITokenizerTest.class.getResource("/sqli/tokenizer").toURI()).listFiles()));

    List<SQLITest> result = Lists.newArrayList();

    for (File file : files) {
      try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
        String line = "";
        reader.readLine();
        String testName = reader.readLine();
        reader.readLine();
        String input = reader.readLine();
        reader.readLine();

        result.add(
            new SQLITest(
                testName,
                input,
                reader
                    .lines()
                    .filter(x -> !x.isBlank())
                    .map(x -> Pair.of(x.charAt(0), x.substring(2)))
                    .collect(Collectors.toList())));
      }
    }

    return result;
  }

  @Test
  public void test() {
    var tokens = tokenizer.tokenize(test.getInput());

    var result =
        tokens.stream()
            .filter(x -> !x.getToken().isBlank())
            .map(x -> Pair.of(x.getType().getTypeName(), x.getToken()))
            .collect(Collectors.toList());

    assertEquals(
        test.getExpected().stream()
            .map(x -> Pair.of(x.getKey(), x.getValue().toLowerCase()))
            .collect(Collectors.toList()),
        result);
  }
}
