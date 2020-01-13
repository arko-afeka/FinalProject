package org.afeka.project.storage;

import com.google.common.cache.*;
import com.google.inject.Inject;
import org.afeka.project.model.http.HTTPMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class ContextStorageImpl implements ContextStorage, RemovalListener<UUID, HTTPMessage> {
  private static final Logger log = LoggerFactory.getLogger(ContextStorage.class);
  private Cache<UUID, HTTPMessage> context;

  @Inject
  public ContextStorageImpl(@StorageDuration Duration storageTime) {
    context =
        CacheBuilder.newBuilder()
            .expireAfterWrite(storageTime.toMillis(), TimeUnit.MILLISECONDS)
            .removalListener(this)
            .build();
  }

  @Override
  public void onRemoval(RemovalNotification<UUID, HTTPMessage> notification) {
    if (notification.getCause() == RemovalCause.EXPIRED) {
      log.info("Didn't get response for {} = {}", notification.getKey(), notification.getValue());
    }
  }

  @Override
  public UUID addToStorage(HTTPMessage message) {
    UUID key = UUID.randomUUID();

    context.put(key, message);

    return key;
  }

  @Override
  public HTTPMessage removeFromStorage(UUID id) {
    return context.getIfPresent(id);
  }
}
