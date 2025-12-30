package ch.lenglet.repository;

import ch.lenglet.auth.AuthenticationConfig;
import ch.lenglet.core.Form;
import ch.lenglet.core.FormFactory;
import com.alibaba.fastjson2.JSON;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.time.Clock;
import java.time.Instant;

import static ch.lenglet.repository.MongoDbConfig.getClientSession;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Sorts.descending;

public class MongoDbFormRepository implements FormRepository{

    private MongoDatabase mongoDatabase;
    private Clock clock;

    @Override
    public Form findLatestByCaseId() {
        final var collection = this.mongoDatabase.getCollection("form");
        final var filter = eq("caseId", 123);
        final var clientSession = getClientSession();
        final var find = clientSession == null ? collection.find(filter) : collection.find(clientSession, filter);

        final var document = find.sort(descending("version"))
                .limit(1)
                .first();
        if (document == null) {
            throw new RuntimeException("No form found for caseId ");
        }
        return FormFactory.createFromJson(document.toJson());
    }

    @Override
    public void save(Form form) {
        form.incrementVersion();
        final var document = Document.parse(JSON.toJSONString(form));
        document.put("timestamp", Instant.now(this.clock));
        document.put("by", AuthenticationConfig.PRINCIPAL.get());

        final var collection = this.mongoDatabase.getCollection("form");
        final var clientSession = getClientSession();
        if (clientSession != null) {
            collection.insertOne(clientSession, document);
        } else {
            collection.insertOne(document);
        }
    }
}
