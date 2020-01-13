package org.afeka.project.storage;

import java.util.concurrent.TimeUnit;

public interface ContextStorageFactory {
  ContextStorage create(int time, TimeUnit unit);
}
