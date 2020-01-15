package org.afeka.project.validation;

import org.afeka.project.model.AnalysisResultState;
import org.afeka.project.model.http.HTTPMessage;
import org.afeka.project.model.http.HTTPMessageType;

import java.util.Set;

public interface ValidationModule {
  AnalysisResultState analyse(HTTPMessage message);

}
