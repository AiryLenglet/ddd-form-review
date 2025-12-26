package ch.lenglet.core;

import com.alibaba.fastjson2.JSONObject;

import java.util.Comparator;
import java.util.Set;
import java.util.stream.Collectors;

import static ch.lenglet.core.Risk.NOT_YET_EVALUATED;

class FormImpl implements Form {

    private long caseId;
    private int version = 0;
    private Set<Answer> answers;
    private Status status;

    private FormImpl() {
    }

    private FormImpl(
            long caseId,
            int version,
            Set<Answer> answers,
            Status status
    ) {
        this.caseId = caseId;
        this.version = version;
        this.answers = answers;
        this.status = status;
    }

    public static FormImpl of(long caseId, Set<Answer> answers) {
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
                            return new Answer(
                                    answer.getString("questionId"),
                                    answer.getString("answerId"),
                                    answer.getObject("rating", Risk.class));
                        }).collect(Collectors.toSet()),
                Status.valueOf(json.getString("status")));
    }

    @Override
    public void reviewAnswer(AnswerId answerId, Risk risk) {
        this.answers.stream()
                .filter(answer -> answerId.id().equals(answer.questionId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Cannot review nonexistent answer " + answerId))
                .rating = risk;
    }

    @Override
    public Risk getWorstRisk() {
        return this.answers.stream()
                .max(Comparator.comparingInt(answer -> switch (answer.rating) {
                    case NOT_APPLICABLE -> 0;
                    case SUFFICIENT -> 1;
                    case INSUFFICIENT -> 2;
                    case NOT_YET_EVALUATED -> 10;
                }))
                .map(answer -> answer.rating)
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

    public static class Answer {
        private String questionId;
        private String answerId;
        private Risk rating = NOT_YET_EVALUATED;

        private Answer() {
        }

        private Answer(
                String questionId,
                String answerId,
                Risk rating
        ) {
            this.questionId = questionId;
            this.answerId = answerId;
            this.rating = rating;
        }

        public static Answer of(
                String questionId,
                String answerId
        ) {
            return new Answer(questionId, answerId, NOT_YET_EVALUATED);
        }

        public String getQuestionId() {
            return questionId;
        }

        public String getAnswerId() {
            return answerId;
        }

        public Risk getRating() {
            return rating;
        }
    }
}
