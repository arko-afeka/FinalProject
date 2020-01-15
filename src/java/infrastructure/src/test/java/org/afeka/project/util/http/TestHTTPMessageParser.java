package org.afeka.project.util.http;

import com.google.common.collect.ImmutableMap;
import org.afeka.project.exception.HTTPStructureException;
import org.afeka.project.model.http.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.assertEquals;

@RunWith(JUnit4.class)
public class TestHTTPMessageParser {
  private HTTPMessageParser parser = new HTTPMessageParserImpl();

  @Test
  public void validRequest() throws HTTPStructureException {
    String data = "GET / HTTP/1.1\r\n" + "Test-Header: 1\r\n\r\n";

    HTTPMessage message = parser.getMessage(data);

    assertEquals(message.getHeaderLine().getType(), HTTPMessageType.Request);

    HTTPRequestLine httpRequestLine = (HTTPRequestLine) message.getHeaderLine();

    assertEquals(httpRequestLine.getMethod(), HTTPMethod.GET);
    assertEquals(httpRequestLine.getHTTPMinorVersion(), 1);
    assertEquals(httpRequestLine.getHTTPMajorVersion(), 1);
    assertEquals(httpRequestLine.getUri(), "/");
    assertEquals(
        message.getHeaders(),
        ImmutableMap.<String, String>builder().put("Test-Header", "1").build());
    assertEquals(message.getBody(), "");
  }

  @Test
  public void validRequestWithBody() throws HTTPStructureException {
    String data = "GET / HTTP/1.1\r\n" + "Test-Header: 1\r\n\r\n" + "test data";

    HTTPMessage message = parser.getMessage(data);

    assertEquals(message.getHeaderLine().getType(), HTTPMessageType.Request);

    HTTPRequestLine httpRequestLine = (HTTPRequestLine) message.getHeaderLine();

    assertEquals(httpRequestLine.getMethod(), HTTPMethod.GET);
    assertEquals(httpRequestLine.getHTTPMinorVersion(), 1);
    assertEquals(httpRequestLine.getHTTPMajorVersion(), 1);
    assertEquals(httpRequestLine.getUri(), "/");
    assertEquals(
        message.getHeaders(),
        ImmutableMap.<String, String>builder().put("Test-Header", "1").build());
    assertEquals(message.getBody(), "test data");
  }

  @Test(expected = HTTPStructureException.class)
  public void illformedRequest() throws HTTPStructureException {
    String data = "GET /id?123 HTTP/1.1\r\n" + "Test-Header: 1 " + "test data";

    HTTPMessage message = parser.getMessage(data);
  }

  @Test(expected = HTTPStructureException.class)
  public void illformedResponse() throws HTTPStructureException {
    String data = "HTTP/1.1 200 OK\r\n" + "Test-Header: 1 " + "test data";

    HTTPMessage message = parser.getMessage(data);
  }
}
