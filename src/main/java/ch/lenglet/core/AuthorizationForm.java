package ch.lenglet.core;

import java.util.Set;

class AuthorizationForm implements Form{

    private final Form delegate;

    private AuthorizationForm(Form delegate) {
        this.delegate = delegate;
    }

    public static AuthorizationForm wrap(Form form) {
        return new AuthorizationForm(form);
    }

    @Override
    public void reviewAnswer(AnswerId answerId, Risk risk) {
        if(this.status() != Status.REVIEW) {
            throw new UnauthorizedOperation("Cannot review answer");
        }
        this.delegate.reviewAnswer(answerId, risk);
    }

    @Override
    public Risk getWorstRisk() {
        return this.delegate.getWorstRisk();
    }

    @Override
    public Set<Answer> answers() {
        return this.delegate.answers();
    }

    @Override
    public String toJson() {
        return this.delegate.toJson();
    }

    @Override
    public Status status() {
        return this.delegate.status();
    }
}
