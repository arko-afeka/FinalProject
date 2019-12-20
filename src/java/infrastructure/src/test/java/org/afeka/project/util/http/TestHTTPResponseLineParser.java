package org.afeka.project.util.http;

import org.afeka.project.exception.HTTPStructureException;
import org.afeka.project.model.http.HTTPResponseLine;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.assertEquals;

@RunWith(JUnit4.class)
public class TestHTTPResponseLineParser {
  @Test(expected = HTTPStructureException.class)
  public void testRequestData() throws Exception {
    String request = "GET / HTTP/1.1";
    new HTTPResponseLineParser(request).parse();
  }

  @Test(expected = HTTPStructureException.class)
  public void testOneSection() throws Exception {
    String request = "HTTP/1.1200Success";
    new HTTPResponseLineParser(request).parse();
  }

  @Test(expected = HTTPStructureException.class)
  public void testNoVersion() throws Exception {
    String request = "200 Success";
    new HTTPResponseLineParser(request).parse();
  }

  @Test
  public void testMoreSections() throws Exception {
    String request = "HTTP/1.1 200 Success test";
    HTTPResponseLine result = (HTTPResponseLine)(new HTTPResponseLineParser(request).parse());
    assertEquals(1, result.getHTTPMajorVersion());
    assertEquals(1, result.getHTTPMinorVersion());
    assertEquals(200, result.getStatusCode());
    assertEquals("Success test", result.getStatusPhrase());
  }

  @Test(expected = HTTPStructureException.class)
  public void testNonNumericVersion() throws Exception {
    String request = "HTTP/a.1 200 Success";
    new HTTPResponseLineParser(request).parse();
  }

  @Test(expected = HTTPStructureException.class)
  public void testMalformedVersion() throws Exception {
    String request = "test 200 Success";
    new HTTPResponseLineParser(request).parse();
  }

  @Test
  public void testValid() throws Exception {
    String request = "HTTP/2.5 404 Not Found";
    HTTPResponseLine result = (HTTPResponseLine)(new HTTPResponseLineParser(request).parse());
    assertEquals(2, result.getHTTPMajorVersion());
    assertEquals(5, result.getHTTPMinorVersion());
    assertEquals(404, result.getStatusCode());
    assertEquals("Not Found", result.getStatusPhrase());
  }

  @Test
  public void testValidExtraSpaces() throws Exception{
    String request = "    HTTP/2.5  200 SUccess   ";
    new HTTPResponseLineParser(request).parse();
  }
}
