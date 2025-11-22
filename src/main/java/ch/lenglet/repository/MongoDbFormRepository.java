package ch.lenglet.repository;

import ch.lenglet.core.Form;
import ch.lenglet.core.FormImpl;
import com.alibaba.fastjson2.JSON;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.time.Clock;
import java.time.Instant;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Sorts.descending;

public class MongoDbFormRepository implements FormRepository{

    private MongoDatabase mongoDatabase;
    private Clock clock;

    @Override
    public Form findLatestByCaseId() {
        final var document = this.mongoDatabase.getCollection("form")
                .find(eq("caseId", 123))
                .sort(descending("version"))
                .limit(1)
                .first();
        if(document == null) {
            throw new RuntimeException("No form found for caseId ");
        }
        return FormImpl.fromJson(document.toJson());
    }

    @Override
    public void save(Form form) {
        final var document = Document.parse(JSON.toJSONString(form));
        document.compute("version", (_, version) -> ((int)version)+1);
        document.put("timestamp", Instant.now(this.clock));
        this.mongoDatabase.getCollection("form")
                .insertOne(document);
    }
}
