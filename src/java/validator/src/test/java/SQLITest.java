import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import model.Stage;
import model.TestFile;
import org.afeka.project.exception.HTTPStructureException;
import org.afeka.project.model.AnalysisResultState;
import org.afeka.project.util.http.HTTPMessageParserImpl;
import org.afeka.project.validation.plugin.SQLiModule;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class SQLITest {
  private model.Test test;
  private SQLiModule module;

  public SQLITest(model.Test test) {
    this.test = test;
  }

  private static List<String> getKnownFailures() {
    return ImmutableList.<String>builder()
            .add("942210-26")
            .add("942210-31")
            .add("942210-42")
            .add("942210-44")
            .add("942210-48")
            .add("942510-2")
            .add("942160-1")
            .add("942511-1")
            .add("942390-1")
            .add("942360-30")
            .build();
  }


  @Parameterized.Parameters(name = "http {index}: {0}")
  public static Collection<model.Test> parameters() throws URISyntaxException, IOException {
    Collection<model.Test> tests = Lists.newArrayList();
    final ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
    for (File file :
        Objects.requireNonNull(
            new File(
                    Objects.requireNonNull(
                            SQLITest.class.getClassLoader().getResource("sqli/injection"))
                        .toURI())
                .listFiles())) {
      final TestFile testFile = objectMapper.readValue(file, TestFile.class);

      if (testFile.getMeta().isEnabled()) {
        tests.addAll(testFile.getTests());
      }
    }

    return tests.stream().filter(x -> !getKnownFailures().contains(x.getTestTitle())).collect(Collectors.toList());
  }

  @Before
  public void setUp() {
    module = new SQLiModule();
  }

  @Test
  public void test() throws MalformedURLException, HTTPStructureException {
    Stage stage = test.getStages().get(0).getStage();

    StringBuffer buffer = new StringBuffer();

    buffer
        .append(stage.getInput().getMethod())
        .append(" ")
        .append(stage.getInput().getUri())
        .append(" ")
        .append(stage.getInput().getVersion())
        .append("\r\n");

    stage
        .getInput()
        .getHeaders()
        .forEach((x, y) -> buffer.append(x).append(": ").append(y).append("\r\n"));

    buffer.append("\r\n\r\n");

    if (stage.getInput().getMethod().equals("POST")) {
      buffer.append(stage.getInput().getData());
    }

    AnalysisResultState result;

    if (Objects.isNull(stage.getOutput().getNoLogContains())) {
      result = AnalysisResultState.ALLOW;
    } else if (Objects.isNull(stage.getOutput().getLogContains())) {
      result = AnalysisResultState.BLOCK;
    } else {
      throw new RuntimeException("Test");
    }

    assertEquals(result, module.analyse(new HTTPMessageParserImpl().getMessage(buffer.toString())));
  }
}
