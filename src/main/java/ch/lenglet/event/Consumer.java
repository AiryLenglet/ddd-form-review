package ch.lenglet.event;

import ch.lenglet.repository.MongoDbConfig;
import com.mongodb.TransactionOptions;
import com.mongodb.WriteConcern;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.TransactionBody;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import org.bson.BsonObjectId;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

import static com.mongodb.client.model.Aggregates.match;
import static com.mongodb.client.model.Filters.*;
import static java.time.Duration.ofHours;
import static java.time.Instant.now;

public class Consumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(Consumer.class);

    private final MongoClient mongoClient;
    private final MongoDatabase mongoDatabase;

    public Consumer(MongoClient client, MongoDatabase mongoDatabase) {
        this.mongoClient = client;
        this.mongoDatabase = mongoDatabase;
    }

    void listenReplicaSet() {
        final var thread = new Thread(() -> mongoDatabase.getCollection("events")
                .watch(List.of(
                        match((in("operationType", List.of("insert", "update")))),
                        match(eq("fullDocument.status", "NEW"))
                )).forEach(document -> {
                    final var key = document.getDocumentKey();
                    System.out.println("key-----");
                    System.out.println(key);
                }), "mongodb-watch");
        thread.start();
    }

    void listen() {
        try (final var session =this.mongoClient.startSession()) {
            LOGGER.info("hasActiveTransaction {} before", session.hasActiveTransaction());
            session.startTransaction(TransactionOptions.builder()
                            .writeConcern(WriteConcern.MAJORITY)
                    .build());
            LOGGER.info("hasActiveTransaction {} after", session.hasActiveTransaction());
            final var event = this.mongoDatabase.getCollection("formEvent")
                    .findOneAndUpdate(
                            session,
                            and(
                                    eq("status", "NEW"),
                                    or(
                                            exists("lock", false),
                                            lt("lock", now().minus(ofHours(1L))))
                            ),
                            Updates.set("lock", now()));
            if (event == null) {
                return;
            }
            int i = 0;
            i++;
            //Thread.sleep(Duration.ofDays(1));

            session.abortTransaction();
            LOGGER.info("end");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String... args) throws InterruptedException {
        final var config = new MongoDbConfig();
        final var client = config.createClient();
        final var db = config.createDatabase(client);

        final var consumer = new Consumer(client, db);
        new Thread(consumer::listen).start();

        while (true) {
            Thread.sleep(Duration.ofSeconds(22));
        }
    }
}
