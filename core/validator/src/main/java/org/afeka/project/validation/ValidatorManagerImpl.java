package org.afeka.project.validation;

import com.google.inject.AbstractModule;
import org.afeka.project.model.AnalysisResult;
import org.afeka.project.model.http.HTTPMessage;

import java.util.UUID;

public class ValidatorManagerImpl extends AbstractModule implements ValidatorManager {
    @Override
    public AnalysisResult validate(HTTPMessage message) {
        return null;
    }

    @Override
    public AnalysisResult validateWithContext(HTTPMessage message, UUID uuid) {
        return null;
    }
}
