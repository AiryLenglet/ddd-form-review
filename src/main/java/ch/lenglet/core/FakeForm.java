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
    public Set<FormImpl.Answer> answers() {
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
}
