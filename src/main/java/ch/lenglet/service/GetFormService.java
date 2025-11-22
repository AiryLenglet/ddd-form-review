package ch.lenglet.service;

import ch.lenglet.repository.AnswerRepository;
import ch.lenglet.repository.FormRepository;

import java.util.Set;
import java.util.stream.Collectors;

public class GetFormService {

    private final FormRepository formRepository;
    private final AnswerRepository answerRepository;

    public GetFormService(
            FormRepository formRepository,
            AnswerRepository answerRepository
    ) {
        this.formRepository = formRepository;
        this.answerRepository = answerRepository;
    }

    FormDto execute() {
        final var form = this.formRepository.findLatestByCaseId();
        final var questions = form.answers().stream()
                .map(answer -> {
                    final var question = this.answerRepository.findById(answer.getQuestionId());
                    final var answers = question.answers().stream()
                            .map(a -> new AnswerDto(a.label(), a.id().equals(answer.getAnswerId())))
                            .collect(Collectors.toSet());
                    return new QuestionDto(answer.getQuestionId(), question.label(), answers);
                })
                .collect(Collectors.toSet());
        return new FormDto(questions);
    }

    record FormDto(
            Set<QuestionDto> questions
    ) {
    }

    record QuestionDto(
            String questionId,
            String questionLabel,
            Set<AnswerDto> answers
    ) {
    }

    record AnswerDto(
            String label,
            Boolean selected
    ) {
    }

}
