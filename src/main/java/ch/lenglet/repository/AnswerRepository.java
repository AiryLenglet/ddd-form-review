package ch.lenglet.repository;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.function.Function.identity;

public class AnswerRepository {

    private final Map<String, Question> questions =
            Stream.of(new Question("v1:q1", "468", "Does person like cats ?", Set.of(
                            new Answer("v1:q1:a1", "469", "Yes"),
                            new Answer("v1:q1:a2", "470", "No"))))
                    .collect(Collectors.toUnmodifiableMap(Question::id, identity()));

    public Question findById(String id) {
        return this.questions.get(id);
    }

    public record Question(
            String id,
            String upstreamId,
            String label,
            Set<Answer> answers
    ) {
    }

    public record Answer(
            String id,
            String upstreamId,
            String label
    ) {
    }
}
