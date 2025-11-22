package ch.lenglet.repository;

import com.mongodb.MongoException;
import com.mongodb.MongoWriteException;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

import static com.mongodb.ErrorCategory.DUPLICATE_KEY;
import static com.mongodb.ErrorCategory.fromErrorCode;

public class MongoDbConfig {

    @SuppressWarnings("resource")
    public MongoDatabase createDatabase() {
        final var client = MongoClients.create("mongodb://root:pass@localhost:27017/");
        return client.getDatabase("test");
    }

    static Exception mapMongoException(MongoException exception) {
        return switch (exception) {
            case MongoWriteException e  when fromErrorCode(e.getCode()) == DUPLICATE_KEY -> new RuntimeException(e);
            default -> exception;
        };
    }
}
