package org.afeka.project.data;

import org.afeka.project.model.http.HTTPMessage;

public interface DataNormalizer {
  HTTPMessage normalize(HTTPMessage message);
}
