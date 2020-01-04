import com.google.common.collect.Lists;
import org.afeka.project.exception.HTTPStructureException;
import org.afeka.project.validation.plugin.xss.Tokenizer;
import org.afeka.project.validation.plugin.xss.model.Token;
import org.afeka.project.validation.plugin.xss.model.TokenType;
import org.afeka.project.validation.plugin.xss.model.ValidatorFlag;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class XSSTokenizerTest {
  private File currentFile;
  private Tokenizer tokenizer;
  private List<Token> tokens;

  @Rule public TestName testName = new TestName();

  public XSSTokenizerTest(String fileName, File currentFile) {
    this.currentFile = currentFile;
  }

  @Before
  public void initialize() throws FileNotFoundException {
    Scanner sc = new Scanner(currentFile);
    sc.nextLine();
    String testName = sc.nextLine();
    testName = testName.replaceAll(".*", testName);
    sc.nextLine();
    String input = sc.nextLine();
    sc.nextLine();

    tokens = Lists.newArrayList();

    while (sc.hasNextLine()) {
      final String output = sc.nextLine();

      if (StringUtils.isBlank(output)) {
        continue;
      }

      String[] data = output.split(",");

      String token;

      if (data.length == 2) {
        token = "";
      } else {
        token = data[2];
      }

      tokens.add(new Token(token, TokenType.valueOf(data[0])));
    }

    this.tokenizer = new Tokenizer(ValidatorFlag.DEFAULT, input);
  }

  @Parameterized.Parameters(name = "test file {index} = {0}")
  public static List<Object[]> getFiles() throws URISyntaxException {
    return Arrays.stream(
            Objects.requireNonNull(
                new File(
                        Objects.requireNonNull(
                                XSSTokenizerTest.class
                                    .getClassLoader()
                                    .getResource("xss/tokenizer"))
                            .toURI())
                    .listFiles()))
        .map(x -> new Object[] {x.getName(), x})
        .collect(Collectors.toList());
  }

  @Test
  public void test() throws HTTPStructureException {
    List<Token> tokens = Lists.newArrayList();

    while (tokenizer.hasNext()) {
      final Token next = tokenizer.next();

      if (Objects.isNull(next)) {
        continue;
      }

      tokens.add(next);
    }

    assertEquals(this.tokens, tokens);
  }
}
