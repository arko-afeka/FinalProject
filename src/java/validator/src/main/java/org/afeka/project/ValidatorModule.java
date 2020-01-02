package org.afeka.project;

import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.google.inject.assistedinject.FactoryModuleBuilder;
import com.google.inject.multibindings.Multibinder;
import org.afeka.project.storage.ContextStorage;
import org.afeka.project.storage.ContextStorageFactory;
import org.afeka.project.storage.ContextStorageImpl;
import org.afeka.project.storage.StorageDuration;
import org.afeka.project.validation.ValidationModule;
import org.afeka.project.validation.ValidatorManager;
import org.afeka.project.validation.ValidatorManagerImpl;
import org.afeka.project.validation.plugin.CSRFModule;
import org.afeka.project.validation.plugin.SQLiModule;
import org.afeka.project.validation.plugin.XSSModule;

import java.time.Duration;

public class ValidatorModule extends AbstractModule {
  @Override
  protected void configure() {
    bind(ValidatorManager.class).to(ValidatorManagerImpl.class);

    install(
        new FactoryModuleBuilder()
            .implement(ContextStorage.class, ContextStorageImpl.class)
            .build(ContextStorageFactory.class));
    bind(Duration.class).annotatedWith(StorageDuration.class).toInstance(Duration.ofMinutes(1L));
    bind(ContextStorage.class).to(ContextStorageImpl.class);

    Multibinder<ValidationModule> validationModuleMultibinder =
        Multibinder.newSetBinder(binder(), ValidationModule.class);
    validationModuleMultibinder.addBinding().to(XSSModule.class);
    validationModuleMultibinder.addBinding().to(SQLiModule.class);
    validationModuleMultibinder.addBinding().to(CSRFModule.class);
  }
}
