package ch.lenglet.workflow;

public class Task {

    private long caseId;
    private long taskId;
    private int version;
    private String assignee;
    private Status status;

    enum Status {
        OPEN,
        DONE
    }

    public void close() {
        this.status = Status.DONE;
    }
}
