package ch.lenglet.core;

import java.util.Set;

public interface Form {

    void reviewAnswer(AnswerId answerId, Risk risk);

    Risk getWorstRisk();

    Set<FormImpl.Answer> answers();

    String toJson();
}
