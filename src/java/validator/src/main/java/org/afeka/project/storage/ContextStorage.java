package org.afeka.project.storage;

import org.afeka.project.model.http.HTTPMessage;

import java.util.UUID;

public interface ContextStorage {
  UUID addToStorage(HTTPMessage message);

  HTTPMessage removeFromStorage(UUID id);
}
