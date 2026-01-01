package ch.lenglet.core;

import java.util.Set;

public class FakeForm implements Form {
    @Override
    public void reviewAnswer(AnswerId answerId, Risk risk) {
    }

    @Override
    public Risk getWorstRisk() {
        return null;
    }

    @Override
    public Set<Answer> answers() {
        return Set.of();
    }

    @Override
    public String toJson() {
        return "";
    }

    @Override
    public Status status() {
        return null;
    }

    @Override
    public Status submitReview() {
        return Status.REVIEW;
    }

    @Override
    public int getVersion() {
        return 0;
    }

    @Override
    public void incrementVersion() {

    }
}
