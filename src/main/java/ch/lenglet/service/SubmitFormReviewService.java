package ch.lenglet.service;

import ch.lenglet.repository.FormRepository;

public class SubmitFormReviewService {

    private final FormRepository formRepository;

    public SubmitFormReviewService(
            FormRepository formRepository
    ) {
        this.formRepository = formRepository;
    }

    void execute() {
        final var form = this.formRepository.findLatestByCaseId();
        final var worstRisk = form.getWorstRisk();
        final var _ = switch (worstRisk) {
            case NOT_YET_EVALUATED -> throw new IllegalStateException("Cannot submit form with un-reviewed answer");
            case NOT_APPLICABLE, SUFFICIENT -> this.nextStep();
            case INSUFFICIENT -> this.goBack();
        };
    }

    Void nextStep() {
        return null;
    }

    Void goBack() {
        return null;
    }
}
