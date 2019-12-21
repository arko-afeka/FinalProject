package org.afeka.project;

import com.google.inject.AbstractModule;
import org.afeka.project.validation.ValidatorManager;
import org.afeka.project.validation.ValidatorManagerImpl;

public class ValidatorModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(ValidatorManager.class).to(ValidatorManagerImpl.class);
    }
}
