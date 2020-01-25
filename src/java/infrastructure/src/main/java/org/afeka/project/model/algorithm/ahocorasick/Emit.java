package org.afeka.project.model.algorithm.ahocorasick;

import java.util.Objects;

public class Emit<V> {
    private String value;
    private V type;

    public Emit(String value, V type) {
        this.value = value;
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public V getType() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Emit<?> emit = (Emit<?>) o;
        return Objects.equals(value, emit.value) &&
                Objects.equals(type, emit.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, type);
    }

    @Override
    public String toString() {
        return "Emit{" +
                "value='" + value + '\'' +
                ", type=" + type +
                '}';
    }
}
