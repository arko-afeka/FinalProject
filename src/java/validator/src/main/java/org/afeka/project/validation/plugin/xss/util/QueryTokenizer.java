package org.afeka.project.validation.plugin.xss.util;

import org.afeka.project.model.http.HTTPMessage;
import org.afeka.project.model.http.HTTPRequestLine;
import org.afeka.project.validation.plugin.xss.MessageTokenizer;
import org.afeka.project.validation.plugin.xss.Tokenizer;
import org.afeka.project.validation.plugin.xss.model.ValidatorFlag;

import java.util.Arrays;
import java.util.stream.Stream;

public class QueryTokenizer implements MessageTokenizer {
  @Override
  public Stream<Tokenizer> get(HTTPMessage message) {
    return Arrays.stream(ValidatorFlag.values())
        .flatMap(
            x ->
                Stream.concat(
                    ((HTTPRequestLine) message.getHeaderLine())
                        .getQueryParams()
                        .values()
                        .parallelStream()
                        .map(y -> new Tokenizer(x, y)),
                    ((HTTPRequestLine) message.getHeaderLine())
                        .getQueryParams()
                        .keySet()
                        .parallelStream()
                        .map(y -> new Tokenizer(x, y))));
  }
}
