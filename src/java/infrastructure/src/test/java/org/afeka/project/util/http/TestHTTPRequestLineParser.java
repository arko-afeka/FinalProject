package org.afeka.project.util.http;

import org.afeka.project.exception.HTTPStructureException;
import org.afeka.project.model.http.HTTPMethod;
import org.afeka.project.model.http.HTTPRequestLine;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.assertEquals;

@RunWith(JUnit4.class)
public class TestHTTPRequestLineParser {
  @Test(expected = HTTPStructureException.class)
  public void testResponseData() throws Exception {
    String request = "HTTP/1.1 200 success";
    new HTTPRequestLineParser(request).parse();
  }

  @Test(expected = HTTPStructureException.class)
  public void testOneSection() throws Exception {
    String request = "GET/HTTP/1.1";
    new HTTPRequestLineParser(request).parse();
  }

  @Test(expected = HTTPStructureException.class)
  public void testNoMethod() throws Exception {
    String request = "  / HTTP/1.1";
    new HTTPRequestLineParser(request).parse();
  }

  @Test(expected = HTTPStructureException.class)
  public void testMoreSections() throws Exception {
    String request = "GET / / HTTP/1.1";
    new HTTPRequestLineParser(request).parse();
  }

  @Test(expected = HTTPStructureException.class)
  public void testNonNumericVersion() throws Exception {
    String request = "GET / HTTP/a.1";
    new HTTPRequestLineParser(request).parse();
  }

  @Test(expected = HTTPStructureException.class)
  public void testRequestLineParserVersion() throws Exception {
    String request = "GET / test";
    new HTTPRequestLineParser(request).parse();
  }

  @Test
  public void testValid() throws Exception {
    String request = "GET / HTTP/1.1";
    HTTPRequestLine result = (HTTPRequestLine) (new HTTPRequestLineParser(request).parse());

    assertEquals(HTTPMethod.GET, result.getMethod());
    assertEquals("/", result.getUri());
    assertEquals(1, result.getHTTPMajorVersion());
    assertEquals(1, result.getHTTPMinorVersion());
  }

  @Test
  public void testValidExtraSpaces() throws Exception {
    String request = "    GET  /  HTTP/1.1   ";

    HTTPRequestLine result = (HTTPRequestLine) (new HTTPRequestLineParser(request).parse());
  }

  @Test
  public void testValidPostWIthURIAndDiffVersion() throws Exception {
    String request = "POST /users/valid?id=1 HTTP/1.0";

    HTTPRequestLine result = (HTTPRequestLine) (new HTTPRequestLineParser(request).parse());

    assertEquals(HTTPMethod.POST, result.getMethod());
    assertEquals("/users/valid?id=1", result.getUri());
    assertEquals(1, result.getHTTPMajorVersion());
    assertEquals(0, result.getHTTPMinorVersion());
  }
}
