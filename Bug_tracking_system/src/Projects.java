public class Projects {
    private  int projectId;
    private  String nameOfProject;

    public Projects() {
    }

    public Projects(int projectId, String nameOfProject) {
        this.projectId = projectId;
        this.nameOfProject = nameOfProject;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public String getNameOfProject() {
        return nameOfProject;
    }

    public void setNameOfProject(String nameOfProject) {
        this.nameOfProject = nameOfProject;
    }
}
