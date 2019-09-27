import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.LogManager;
import java.util.logging.Logger;



public class Main {
    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) throws SQLException, ClassNotFoundException, IOException {
        LogManager.getLogManager().readConfiguration();
        final Handler fileHandler = new FileHandler("mylog.txt");
        LOGGER.addHandler(fileHandler);
        LOGGER.setUseParentHandlers(false);
        DBConnection dbConnection = new DBConnection();
        LOGGER.warning("Создали сканнер");
        Scanner input = new Scanner(System.in);
        LOGGER.info("Подключаемся к базе данных");
        dbConnection.Conn();
        LOGGER.info("Создаем если ее еще нет, базу данных");
        dbConnection.CreateDB();
        boolean flag=true;
        while (flag)
        {
            LOGGER.info("Вывод всех функции на консоль");
            getList();
            LOGGER.info("Выбор функции");
            int n = input.nextInt();
            LOGGER.info("Инициализируем коллекции");
            ArrayList<Users> usrs = dbConnection.getAllUsers();
            ArrayList<Tasks> tasksArrayList = dbConnection.getAllTasks();
            ArrayList<Projects> projectsArrayList= dbConnection.getAllProjects();
            if (n==1) {
                LOGGER.info("Выбрана функция создания пользователя");
                System.out.println("Введите Имя и Фамилию и название файла");
                String name=input.next();
                String surname=input.next();
                String fileName=input.next();
                LOGGER.info("ИСпользование метода AddUsers");
                dbConnection.AddUsers(new Users(0,name,surname));
                try {
                    LOGGER.info("Даем имя файлу");
                    dbConnection.setFileName(fileName);
                    LOGGER.info("Педключаемся к файлу");
                    dbConnection.Conn();
                }catch (NullPointerException e)
                {
                    e.printStackTrace();
                    LOGGER.warning("Ошибка NUllPOINTER");
                }
            }
            else if(n==2){
                LOGGER.info("Выбрана функция создания проекта");
                System.out.println("Введите название проекта");
                String nameOfProject=input.next();
                LOGGER.info("Использован метод AddProjects");
                dbConnection.AddProjects(new Projects(0,nameOfProject));
            }
            else if(n==3){
                LOGGER.info("Выбрана функция создания задач");
                System.out.println("Введите Названия:задачи,проекта,темы,типа,приоритета,ID исполнителя и описание");
                String nameOfTask = input.next();
                String project=input.next();
                String topic = input.next();
                String type = input.next();
                String priority = input.next();
                int user_id = input.nextInt();
                String description = input.next();
                LOGGER.info("Проверяем на наличие таких пользователей или проектов");
                if(dbConnection.check(project,user_id))
                {
                    LOGGER.info("Вызван метод AddTasks");
                    dbConnection.AddTasks(new Tasks(0,nameOfTask,project,topic,type,priority,user_id,description));
                }
                else
                    System.err.println("Такого проекта или пользователя не существует");
            }
            else if(n==4)
            {
                LOGGER.info("Выбрана фунция получения всех пользователей");
                for (int i = 0; i <usrs.size(); i++) {
                    System.out.println("ID = "+usrs.get(i).getId());
                    System.out.println("Name = "+usrs.get(i).getName());
                    System.out.println("Surname = "+usrs.get(i).getSurname());
                    System.out.println("----------------------------------------");
                }
            }
            else if(n==5)
            {
                LOGGER.info("Вызвана функция получения всех проектов");
                for (int i = 0; i <projectsArrayList.size(); i++) {
                    System.out.println("Project ID = "+projectsArrayList.get(i).getProjectId());
                    System.out.println("nameOfProject = "+projectsArrayList.get(i).getNameOfProject());
                    System.out.println("--------------------------------------------------");
                }
            }
            else if(n==6)
            {
                LOGGER.info("Вызвана функция получения всех задач");
                for (int i = 0; i <tasksArrayList.size(); i++) {
                    System.out.println("Task ID = "+tasksArrayList.get(i).getTask_id());
                    System.out.println("Name of Task"+tasksArrayList.get(i).getNameOfTask());
                    System.out.println("project = "+tasksArrayList.get(i).getProject());
                    System.out.println("topic = "+tasksArrayList.get(i).getTopic());
                    System.out.println("type = "+tasksArrayList.get(i).getType());
                    System.out.println("priority = "+tasksArrayList.get(i).getPriority());
                    System.out.println("user ID = "+tasksArrayList.get(i).getUser());
                    System.out.println("description = "+tasksArrayList.get(i).getDescription());
                    System.out.println("-------------------------------------------------");
                }
            }
            else if(n==7)
            {
                LOGGER.info("Вызвана функция удаления задачи по айди");
                System.out.println("Введите ID ");
                int id = input.nextInt();
                dbConnection.deleteTaskById(id);
            }
            else if(n==8)
            {
                LOGGER.info("Вызвана функция удаления проекта по айди");
                System.out.println("Введите ID ");
                int id = input.nextInt();
                dbConnection.deleteProjectById(id);
            }
            else if(n==9)
            {
                LOGGER.info("Вызвана функция удаления пользователей по айди");
                System.out.println("Введите ID ");
                int id = input.nextInt();
                dbConnection.deleteUserById(id);
            }
            else if (n==10)
            {
                LOGGER.info("Вызвана функция получения всех задач пользователя");
                System.out.println("Введите ID пользователя");
                int id=input.nextInt();
                dbConnection.getAllUserTasks(id);
            }
            else if(n==11)
            {
                break;
            }
            else
            {
                System.out.println("Вы ввели некорректно");
                LOGGER.warning("Ввод в консоль произошел неправильно");
            }
        }

        LOGGER.info("Закрываем БД");
        dbConnection.CloseDB();



    }
    private static void getList()
    {
        System.out.println("1.Создание Пользователя");
        System.out.println("2.Создание Проекта");
        System.out.println("3.Создание Задачи");
        System.out.println("4.Получение списка всех пользователей");
        System.out.println("5.Получение списка всех проектов");
        System.out.println("6.Получение списка всех задач");
        System.out.println("7.Удалить задачу");
        System.out.println("8.Удалить проект");
        System.out.println("9.Удалить пользователя");
        System.out.println("10.Получить список всех задач пользователя");
        System.out.println("11.Выйти");

    }
}
