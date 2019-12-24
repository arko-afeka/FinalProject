package org.afeka.project;

import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import org.afeka.project.storage.ContextStorage;
import org.afeka.project.storage.ContextStorageImpl;
import org.afeka.project.validation.ValidatorManager;
import org.afeka.project.validation.ValidatorManagerImpl;

public class ValidatorModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(ValidatorManager.class).to(ValidatorManagerImpl.class);
        bind(ContextStorage.class).to(ContextStorageImpl.class);
    }
}
