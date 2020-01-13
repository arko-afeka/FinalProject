package org.afeka.project.validation.plugin.xss.util;

import org.afeka.project.model.http.HTTPMessage;
import org.afeka.project.validation.plugin.xss.MessageTokenizer;
import org.afeka.project.validation.plugin.xss.Tokenizer;
import org.afeka.project.validation.plugin.xss.model.ValidatorFlag;

import java.util.Arrays;
import java.util.stream.Stream;

public class BodyTokenizer implements MessageTokenizer {
  @Override
  public Stream<Tokenizer> get(HTTPMessage message) {
    return Arrays.stream(ValidatorFlag.values())
        .parallel()
        .map(x -> new Tokenizer(x, message.getBody()));
  }
}
