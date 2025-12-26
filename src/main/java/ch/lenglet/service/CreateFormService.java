package ch.lenglet.service;

import ch.lenglet.core.Form;
import ch.lenglet.core.FormFactory;
import ch.lenglet.core.InternalAnswer;
import ch.lenglet.repository.FormRepository;

import java.util.Set;
import java.util.stream.Collectors;

public class CreateFormService {

    private final FormRepository formRepository;

    public CreateFormService(
            FormRepository formRepository
    ) {
        this.formRepository = formRepository;
    }

    void save(
            UpstreamFormData upstreamFormData
    ) {
        final var form = switch (upstreamFormData) {
            case V1UpstreamFormData data -> this.create(data);
            case V2UpstreamFormData _ -> throw new RuntimeException("Not yet implemented");
        };
        this.formRepository.save(form);
    }

    private Form create(V1UpstreamFormData upstreamForm) {
        final var answers = upstreamForm.questionAnswers().stream()
                .map(questionAnswer -> InternalAnswer.of(
                        questionAnswer.questionId(),
                        questionAnswer.selectedAnswerId()))
                .collect(Collectors.toSet());
        return FormFactory.newForm(22L, answers);
    }

    record KycQuestionAnswerDto(
            String questionId,
            String selectedAnswerId
    ) {
    }

    sealed interface UpstreamFormData permits V1UpstreamFormData, V2UpstreamFormData{
    }

    record V1UpstreamFormData(
            Set<KycQuestionAnswerDto> questionAnswers
    ) implements UpstreamFormData {}

    record V2UpstreamFormData(
    ) implements UpstreamFormData {}

    enum FormVersion {
        V1,
        v2
    }
}
