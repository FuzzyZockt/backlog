package backlog;

public class Task {
    private String name;
    private String description;
    private String status;
    private String deadline;
    private String assignee;

    public Task(String name, String description, String status, String deadline, String assignee) {
        this.name = name;
        this.description = description;
        this.status = status;
        this.deadline = deadline;
        this.assignee = assignee;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getStatus() {
        return status;
    }

    public String getDeadline() {
        return deadline;
    }

    public String getAssignee() {
        return assignee;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }

    @Override
    public String toString() {
        return name + " (" + status + ", Deadline: " + deadline + ", Assignee: " + assignee + ")";
    }
}