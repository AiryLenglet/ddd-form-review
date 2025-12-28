package ch.lenglet.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Transaction {

    WriteConcern writeConcern() default WriteConcern.MAJORITY;

    enum WriteConcern {
        MAJORITY (com.mongodb.WriteConcern.MAJORITY)
        ;

        private final com.mongodb.WriteConcern writeConcern;

        WriteConcern(com.mongodb.WriteConcern writeConcern) {
            this.writeConcern = writeConcern;
        }

        public com.mongodb.WriteConcern get() {
            return writeConcern;
        }
    }
}
