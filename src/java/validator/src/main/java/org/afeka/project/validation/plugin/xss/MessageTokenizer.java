package org.afeka.project.validation.plugin.xss;

import org.afeka.project.model.http.HTTPMessage;

import java.util.stream.Stream;

public interface MessageTokenizer {
    Stream<Tokenizer> get(HTTPMessage message);
}
