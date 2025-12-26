package ch.lenglet.core;

import static ch.lenglet.core.Risk.NOT_YET_EVALUATED;

public class InternalAnswer implements Answer {

    private String questionId;
    private String answerId;
    private Risk rating = NOT_YET_EVALUATED;

    private InternalAnswer() {
    }

    InternalAnswer(
            String questionId,
            String answerId,
            Risk rating
    ) {
        this.questionId = questionId;
        this.answerId = answerId;
        this.rating = rating;
    }

    public static InternalAnswer of(
            String questionId,
            String answerId
    ) {
        return new InternalAnswer(questionId, answerId, NOT_YET_EVALUATED);
    }

    @Override
    public String getQuestionId() {
        return questionId;
    }

    @Override
    public String getAnswerId() {
        return answerId;
    }

    @Override
    public Risk getRating() {
        return rating;
    }

    public void rate(Risk risk) {
        this.rating = risk;
    }
}
