package ch.lenglet.core;

public interface Aggregate {

    int getVersion();

    void incrementVersion();
}
