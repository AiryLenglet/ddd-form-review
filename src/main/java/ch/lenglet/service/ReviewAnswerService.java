package ch.lenglet.service;

import ch.lenglet.core.AnswerId;
import ch.lenglet.core.Risk;
import ch.lenglet.repository.FormRepository;

public class ReviewAnswerService {

    private final FormRepository formRepository;

    public ReviewAnswerService(
            FormRepository formRepository
    ) {
        this.formRepository = formRepository;
    }

    void execute() {
        final var form = this.formRepository.findLatestByCaseId();
        form.reviewAnswer(new AnswerId(), Risk.SUFFICIENT);
        this.formRepository.save(form);
    }
}
