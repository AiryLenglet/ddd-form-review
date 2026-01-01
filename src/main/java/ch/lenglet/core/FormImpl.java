package ch.lenglet.core;

import com.alibaba.fastjson2.JSONObject;

import java.util.Comparator;
import java.util.Set;
import java.util.stream.Collectors;

class FormImpl implements Form {

    private long caseId;
    private int version = 0;
    private Set<InternalAnswer> answers;
    private Status status;

    private FormImpl() {
    }

    private FormImpl(
            long caseId,
            int version,
            Set<InternalAnswer> answers,
            Status status
    ) {
        this.caseId = caseId;
        this.version = version;
        this.answers = answers;
        this.status = status;
    }

    public static FormImpl of(long caseId, Set<InternalAnswer> answers) {
        return new FormImpl(caseId, 0, Set.copyOf(answers), Status.REVIEW);
    }

    public static FormImpl fromJson(String jsonString) {
        final var json = JSONObject.parseObject(jsonString);
        return new FormImpl(
                json.getLongValue("caseId"),
                json.getIntValue("version"),
                json.getJSONArray("answers").stream()
                        .map(o -> {
                            final var answer = (JSONObject) o;
                            return new InternalAnswer(
                                    answer.getString("questionId"),
                                    answer.getString("answerId"),
                                    answer.getObject("rating", Risk.class));
                        }).collect(Collectors.toSet()),
                Status.valueOf(json.getString("status")));
    }

    @Override
    public void reviewAnswer(AnswerId answerId, Risk risk) {
        this.answers.stream()
                .filter(answer -> answerId.id().equals(answer.getQuestionId()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Cannot review nonexistent answer " + answerId))
                .rate(risk);
    }

    @Override
    public Risk getWorstRisk() {
        return this.answers.stream()
                .max(Comparator.comparingInt(answer -> switch (answer.getRating()) {
                    case NOT_APPLICABLE -> 0;
                    case SUFFICIENT -> 1;
                    case INSUFFICIENT -> 2;
                    case NOT_YET_EVALUATED -> 10;
                }))
                .map(Answer::getRating)
                .orElseThrow(() -> new IllegalStateException("Cannot estimate risk of empty form"));
    }

    @Override
    public Set<Answer> answers() {
        return Set.copyOf(this.answers);
    }

    @Override
    public String toJson() {
        return JSONObject.of(
                "caseId", this.caseId,
                "version", this.version,
                "answers", this.answers,
                "status", this.status
        ).toJSONString();
    }

    @Override
    public Status status() {
        return this.status;
    }

    @Override
    public int getVersion() {
        return this.version;
    }

    @Override
    public void incrementVersion() {
        this.version += 1;
    }

    @Override
    public Status submitReview() {
        this.status = switch (this.getWorstRisk()) {
            case NOT_YET_EVALUATED -> throw new IllegalStateException("Cannot submit form with un-reviewed answer");
            case NOT_APPLICABLE, SUFFICIENT -> Status.APPROVED;
            case INSUFFICIENT -> Status.SCORING;
        };
        return this.status;
    }
}
