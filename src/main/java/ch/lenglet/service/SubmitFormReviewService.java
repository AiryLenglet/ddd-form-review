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
        final var _ = switch (form.submitReview()) {
            case REVIEW -> null;
            case APPROVED -> this.nextStep();
            case SCORING -> this.goBack();
        };
    }

    Void nextStep() {
        return null;
    }

    Void goBack() {
        return null;
    }
}
