package org.afeka.project;

import com.google.inject.AbstractModule;
import com.google.inject.assistedinject.FactoryModuleBuilder;
import com.google.inject.multibindings.Multibinder;
import com.google.inject.name.Names;
import org.afeka.project.validation.ValidationModule;
import org.afeka.project.validation.ValidationModuleConfiguration;
import org.afeka.project.validation.ValidatorManager;
import org.afeka.project.validation.ValidatorManagerImpl;
import org.afeka.project.validation.plugin.CSRFModule;
import org.afeka.project.validation.plugin.SQLiModule;
import org.afeka.project.validation.plugin.XSSModule;
import org.afeka.project.validation.plugin.csrf.CSRFConfiguration;
import org.afeka.project.validation.plugin.xss.MessageTokenizer;
import org.afeka.project.validation.plugin.xss.util.BodyTokenizer;
import org.afeka.project.validation.plugin.xss.util.HeaderTokenizer;
import org.afeka.project.validation.plugin.xss.util.QueryTokenizer;

import java.time.Duration;

public class ValidatorModule extends AbstractModule {
  @Override
  protected void configure() {
    bind(ValidatorManager.class).to(ValidatorManagerImpl.class);

    Multibinder<MessageTokenizer> tokenizerMultibinder = Multibinder.newSetBinder(binder(), MessageTokenizer.class);
    tokenizerMultibinder.addBinding().to(BodyTokenizer.class);
    tokenizerMultibinder.addBinding().to(HeaderTokenizer.class);
    tokenizerMultibinder.addBinding().to(QueryTokenizer.class);

    Multibinder<ValidationModule> validationModuleMultibinder =
        Multibinder.newSetBinder(binder(), ValidationModule.class);
    validationModuleMultibinder.addBinding().to(XSSModule.class);
    validationModuleMultibinder.addBinding().to(SQLiModule.class);
    validationModuleMultibinder.addBinding().to(CSRFModule.class);

    bind(ValidationModuleConfiguration.class).annotatedWith(Names.named("csrf")).to(CSRFConfiguration.class);
  }
}
