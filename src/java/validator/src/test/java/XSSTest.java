import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.google.common.collect.Lists;
import com.google.inject.Guice;
import com.google.inject.Injector;
import model.Stage;
import model.TestFile;
import org.afeka.project.ValidatorModule;
import org.afeka.project.exception.HTTPStructureException;
import org.afeka.project.model.AnalysisResultState;
import org.afeka.project.model.http.HTTPMessage;
import org.afeka.project.util.http.HTTPMessageParserImpl;
import org.afeka.project.validation.plugin.XSSModule;
import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.Objects;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class XSSTest {
  private model.Test test;
  private Injector injector = Guice.createInjector(new ValidatorModule());
  private XSSModule module;

  public XSSTest(model.Test test) {
    this.test = test;
  }

  @Parameterized.Parameters(name = "http {index}: {0}")
  public static Collection<model.Test> parameters() throws URISyntaxException, IOException {
    Collection<model.Test> tests = Lists.newArrayList();
    final ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
    for (File file :
        Objects.requireNonNull(
            new File(
                    Objects.requireNonNull(
                            XSSTest.class.getClassLoader().getResource("xss/injection"))
                        .toURI())
                .listFiles())) {
      final TestFile testFile = objectMapper.readValue(file, TestFile.class);

      if (testFile.getMeta().isEnabled()) {
        tests.addAll(testFile.getTests());
      }
    }

    return tests;
  }

  @Before
  public void setUp() {
    module = injector.getInstance(XSSModule.class);
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
        .append(Objects.requireNonNullElse(stage.getInput().getVersion(), "HTTP/1.1"))
        .append("\r\n");

    stage
        .getInput()
        .getHeaders()
        .forEach((x, y) -> buffer.append(x).append(": ").append(y).append("\r\n"));

    buffer.append("\r\n");

    if (Objects.nonNull(stage.getInput().getData())) {
      buffer.append(StringUtils.join(stage.getInput().getData(), "\r\n"));
    }

    AnalysisResultState result;

    if (Objects.isNull(stage.getOutput().getLogContains())) {
      result = AnalysisResultState.ALLOW;
    } else if (Objects.isNull(stage.getOutput().getNoLogContains())) {
      result = AnalysisResultState.BLOCK;
    } else {
      throw new RuntimeException("Test");
    }

    System.out.println(String.format("Input is: %s", buffer.toString()));
    assertEquals(result, module.analyse(new HTTPMessageParserImpl().getMessage(buffer.toString())));
  }
}
