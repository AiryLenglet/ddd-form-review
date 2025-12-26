package ch.lenglet.repository;

import com.mongodb.MongoException;
import com.mongodb.MongoWriteException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

import static com.mongodb.ErrorCategory.DUPLICATE_KEY;
import static com.mongodb.ErrorCategory.fromErrorCode;

public class MongoDbConfig {

    public static ScopedValue<MongoClient> MONGO_CLIENT = ScopedValue.newInstance();

    public MongoClient createClient() {
        return MongoClients.create("mongodb://root:pass@localhost:27017/?retryWrites=false&replicaSet=rs0");
    }

    @SuppressWarnings("resource")
    public MongoDatabase createDatabase(MongoClient client) {
        return client.getDatabase("test");
    }

    static Exception mapMongoException(MongoException exception) {
        return switch (exception) {
            case MongoWriteException e  when fromErrorCode(e.getCode()) == DUPLICATE_KEY -> new RuntimeException(e);
            default -> exception;
        };
    }
}
