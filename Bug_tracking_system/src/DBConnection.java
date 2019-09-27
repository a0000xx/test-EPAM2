import java.sql.*;
import java.util.ArrayList;

;


public class DBConnection {
    public static Connection conn;
    public static Statement statmt;
    public static ResultSet resSet;
    public String fileName="test";
    public void setFileName(String name)
    {
        if(!name.isEmpty())
        fileName = name;
    }


    // --------ПОДКЛЮЧЕНИЕ К БАЗЕ ДАННЫХ--------
    public void Conn() throws ClassNotFoundException, SQLException
    {
        conn = null;
        Class.forName("org.sqlite.JDBC");
        conn = DriverManager.getConnection("jdbc:sqlite:"+fileName+".db");

        System.err.println("База Подключена!");
    }

    // --------Создание таблицы--------
    public void CreateDB() throws ClassNotFoundException, SQLException
    {
        statmt = conn.createStatement();
        statmt.execute("CREATE TABLE if not exists 'User' ('id' INTEGER PRIMARY KEY AUTOINCREMENT, 'name' NVARCHAR(80), 'surname' NVARCHAR(80)); ");
        statmt.execute("CREATE TABLE if not exists 'Task' ('task_id' INTEGER PRIMARY KEY AUTOINCREMENT,'nameOfTask' NVARCHAR(80), 'project' NVARCHAR(80), 'topic' NVARCHAR(80),'type' NVARCHAR(80)," +
                " 'priority' NVARCHAR(80), 'user_id' INT, 'description' Text); ");
        statmt.execute("CREATE TABLE if not exists 'Project' ('project_id' INTEGER PRIMARY KEY AUTOINCREMENT, 'nameOfProject' NVARCHAR(80)); ");
        System.err.println("Таблица создана или уже существует.");
    }

    // --------Заполнение таблицы--------
    public void AddUsers(Users users)
    {
        try {
            statmt.execute("INSERT INTO 'User' ('name','surname') VALUES ('"+users.getName()+"','"+users.getSurname()+"');");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.err.println("Таблица пользователей заполнена");
    }

    // -------- Вывод таблицы--------
    public ArrayList<Users> getAllUsers() throws ClassNotFoundException, SQLException,NullPointerException {
        resSet = statmt.executeQuery("SELECT * FROM User");
        ArrayList<Users> users = new ArrayList();
        while (resSet.next()) {
            int id= resSet.getInt("id");
            String name = resSet.getString("name");
            String surname=resSet.getString("surname");
            users.add(new Users(id,name,surname));
        }
        return users;
    }
    public void AddTasks(Tasks tasks)
    {
        try {
            statmt.execute("INSERT INTO 'Task' ('nameOfTask','project','topic','type','priority','user_id','description') " +
                    "VALUES ('"+tasks.getNameOfTask()+"','"+tasks.getProject()+"','"+tasks.getTopic()+"','"+tasks.getType()+"','"+tasks.getPriority()+"','"+tasks.getUser()+"','"+tasks.getDescription()+"')");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.err.println("Таблица задач заполнена");
    }
    public ArrayList<Tasks> getAllTasks() throws ClassNotFoundException, SQLException,NullPointerException {
        resSet = statmt.executeQuery("SELECT * FROM Task");
        ArrayList<Tasks> tasks = new ArrayList();
        while (resSet.next()) {
            int id= resSet.getInt("task_id");
            String nameOfTask = resSet.getString("nameOfTask");
            String project = resSet.getString("project");
            String topic=resSet.getString("topic");
            String type  = resSet.getString("type");
            String priority=resSet.getString("priority");
            int user=resSet.getInt("user_id");
            String description=resSet.getString("description");
            tasks.add(new Tasks(id,nameOfTask,project,topic,type,priority,user,description));
        }
        return tasks;
    }

    public void AddProjects(Projects projects)
    {
        try {
            statmt.execute("INSERT INTO 'Project' ('nameOfProject') VALUES ('"+projects.getNameOfProject()+"')");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public ArrayList<Projects> getAllProjects() throws SQLException,NullPointerException{
        resSet = statmt.executeQuery("SELECT * FROM Project");
        ArrayList<Projects> projects = new ArrayList();
        while (resSet.next())
        {
            int project_Id = resSet.getInt("project_id");
            String nameOfProject = resSet.getString("nameOfProject");
            projects.add(new Projects(project_Id,nameOfProject));
        }
        return projects;
    }
    public void deleteUserById(int id)
    {
        try (PreparedStatement statement = this.conn.prepareStatement(
                "DELETE FROM User WHERE id = ?")) {
            statement.setInt(1,id);
            // Выполняем запрос
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void deleteProjectById(int id)
    {
        try (PreparedStatement statement = this.conn.prepareStatement(
                "DELETE FROM Project WHERE project_id = ?")) {
            statement.setInt(1,id);
            // Выполняем запрос
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void deleteTaskById(int id)
    {
        try (PreparedStatement statement = this.conn.prepareStatement(
                "DELETE FROM Task WHERE task_id = ?")) {
            statement.setInt(1,id);
            // Выполняем запрос
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public boolean check(String project,int user_id) throws SQLException, ClassNotFoundException {
        int count=0;
        ArrayList<Users> users = getAllUsers();
        ArrayList<Projects> projects = getAllProjects();
        System.err.println(users.size());
        for (int i = 0; i <users.size() ; i++) {
            if(users.get(i).getId()==user_id)
                count++;
        }
        for (int i = 0; i <projects.size() ; i++) {
            if(projects.get(i).getNameOfProject().equals(project))
                count++;
        }
        //System.err.println(count);
        if(count>=2)
            return true;
        return false;
    }
    public void getAllUserTasks(int id) throws SQLException, ClassNotFoundException {
        ArrayList<Tasks> tasks = getAllTasks();
        System.out.println(tasks.size());
        for (int i = 0; i <tasks.size() ; i++) {
            if(tasks.get(i).getUser()==id)
            {
                System.out.println(tasks.get(i).getNameOfTask());
            }
        }
    }


    public void CloseDB()
    {
        try {
            conn.close();
            statmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.err.println("Соединения закрыты");
    }

}
