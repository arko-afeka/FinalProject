import com.google.common.collect.Lists;
import org.afeka.project.libinjection.Libinjection;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
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
public class SQLITokenizerTest {
  private File currentFile;
  private Libinjection libinjection;
  private List<Pair<String, String>> expected;
  private String input;

  public SQLITokenizerTest(String fileName, File currentFile) {
    this.currentFile = currentFile;
  }

  @Before
  public void initialize() throws FileNotFoundException {
    Scanner sc = new Scanner(currentFile);
    sc.nextLine();
    sc.nextLine();
    sc.nextLine();
    input = sc.nextLine();
    sc.nextLine();
    expected = Lists.newArrayList();

    while (sc.hasNextLine()) {
      String data = sc.nextLine();
      if (StringUtils.isBlank(data)) {
        continue;
      }

      final String[] typeAndData = data.split(" ");
      expected.add(Pair.of(typeAndData[0], typeAndData[1]));
    }

    libinjection = new Libinjection();
  }

  @Parameterized.Parameters(name = "test file {index} = {0}")
  public static List<Object[]> getFiles() throws URISyntaxException {
    return Arrays.stream(
            Objects.requireNonNull(
                new File(
                        Objects.requireNonNull(
                                SQLITokenizerTest.class
                                    .getClassLoader()
                                    .getResource("sqli/tokenizer"))
                            .toURI())
                    .listFiles()))
        .map(x -> new Object[] {x.getName(), x})
        .collect(Collectors.toList());
  }

  @Test
  @Ignore
  public void test() {
    libinjection.libinjection_sqli(input);
    while (libinjection.libinjection_sqli_tokenize()) {}

    assertEquals(
        expected,
        Arrays.stream(libinjection.getState().getTokenvec())
            .filter(Objects::nonNull)
            .map(x -> Pair.of(x.getType(), x.getVal()))
            .collect(Collectors.toList()));
  }
}
