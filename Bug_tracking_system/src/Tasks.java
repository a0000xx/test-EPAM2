
public class Tasks {
    private int task_id;
    private String nameOfTask;
    private String project;
    private String topic;
    private String type;
    private String priority;
    private int user_id;
    private String description;

    public Tasks() {
    }

    public Tasks(int task_id, String nameOfTask, String project, String topic, String type, String priority, int user_id, String description) {
        this.task_id = task_id;
        this.nameOfTask = nameOfTask;
        this.project = project;
        this.topic = topic;
        this.type = type;
        this.priority = priority;
        this.user_id = user_id;
        this.description = description;
    }

    public Tasks(int task_id, String nameOfTask, String project, int user_id) {
        this.task_id = task_id;
        this.nameOfTask = nameOfTask;
        this.project = project;
        this.user_id = user_id;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPriority() {
        return priority;
    }

    public int getTask_id() {
        return task_id;
    }

    public void setTask_id(int task_id) {
        this.task_id = task_id;
    }

    public String getNameOfTask() {
        return nameOfTask;
    }

    public void setNameOfTask(String nameOfTask) {
        this.nameOfTask = nameOfTask;
    }
    public void setPriority(String priority) {
        this.priority = priority;
    }

    public int getUser() {
        return user_id;
    }

    public void setUser(int user) {
        this.user_id = user;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
