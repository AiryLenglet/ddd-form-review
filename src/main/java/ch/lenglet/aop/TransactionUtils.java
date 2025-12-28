package ch.lenglet.aop;

import com.mongodb.TransactionOptions;
import com.mongodb.client.ClientSession;
import com.mongodb.client.MongoClient;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Supplier;

import static ch.lenglet.repository.MongoDbConfig.CLIENT_SESSION;
import static ch.lenglet.repository.MongoDbConfig.MONGO_CLIENT;

public class TransactionUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger("Transaction");

    public static Object wrapInTransaction(Supplier<Object> methodExecution, JoinPoint jp) {
        LOGGER.info("Opening transaction");
        final var methodSignature = (MethodSignature) jp.getSignature();
        final var method = methodSignature.getMethod();
        final var transactionAnnotation = method.getAnnotation(Transaction.class);
        final MongoClient mongoClient = MONGO_CLIENT.get();
        ClientSession session = mongoClient.startSession();
        try (session){
            final var options = TransactionOptions.builder()
                    .writeConcern(transactionAnnotation.writeConcern().get())
                            .build();
            session.startTransaction(options);
            final var result = ScopedValue.where(CLIENT_SESSION, session)
                    .call(methodExecution::get);
            session.commitTransaction();
            LOGGER.info("Commiting transaction");
            return result;
        } catch (Exception e) {
            LOGGER.info("Exception occurred, aborting transaction");
            session.abortTransaction();
            throw e;
        }
    }
}
