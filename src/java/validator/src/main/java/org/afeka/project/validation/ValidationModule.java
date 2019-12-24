package org.afeka.project.validation;

import org.afeka.project.model.AnalysisResultState;
import org.afeka.project.model.http.HTTPMessage;

public interface ValidationModule {
    AnalysisResultState analyse(HTTPMessage message);
    AnalysisResultState analyseWithContext(HTTPMessage message, HTTPMessage fromStorage);

    boolean validateRequest();
    boolean validatesResponse();
    boolean requiresContext();
}
