package ch.lenglet.core;

public class UnauthorizedOperation extends RuntimeException {
    public UnauthorizedOperation(String message) {
        super(message);
    }
}
