package ch.lenglet.core;

import java.util.Set;

public interface Form extends Aggregate{

    void reviewAnswer(AnswerId answerId, Risk risk);

    Risk getWorstRisk();

    Set<Answer> answers();

    String toJson();

    Status status();

    enum Status {
        REVIEW
    }
}
