package ch.lenglet.service;

import ch.lenglet.aop.Transaction;
import ch.lenglet.repository.FormRepository;
import ch.lenglet.repository.TaskRepository;

public class SubmitFormReviewService {

    private final FormRepository formRepository;
    private final TaskRepository taskRepository;

    public SubmitFormReviewService(
            FormRepository formRepository,
            TaskRepository taskRepository
    ) {
        this.formRepository = formRepository;
        this.taskRepository = taskRepository;
    }

    @Transaction
    void execute(String taskId) {
        final var task = this.taskRepository.findLatestByTaskId(taskId);
        task.close();
        this.taskRepository.save(task);

        final var form = this.formRepository.findLatestByCaseId();
        final var _ = switch (form.submitReview()) {
            case REVIEW -> null;
            case APPROVED -> this.closeWorkflow();
            case SCORING -> this.createNewScoringTask();
        };
    }

    Void closeWorkflow() {
        return null;
    }

    Void createNewScoringTask() {
        return null;
    }
}
