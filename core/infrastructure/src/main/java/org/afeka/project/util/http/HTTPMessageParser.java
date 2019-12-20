package org.afeka.project.util.http;

import org.afeka.project.exception.HTTPStructureException;
import org.afeka.project.model.http.HTTPMessage;

public interface HTTPMessageParser {
  HTTPMessage getMessage(String data) throws HTTPStructureException;
}
