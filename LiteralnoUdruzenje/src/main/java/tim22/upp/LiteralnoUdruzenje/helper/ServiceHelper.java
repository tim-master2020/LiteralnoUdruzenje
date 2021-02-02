package tim22.upp.LiteralnoUdruzenje.helper;

import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tim22.upp.LiteralnoUdruzenje.service.IBookService;
import tim22.upp.LiteralnoUdruzenje.service.IUserService;
import tim22.upp.LiteralnoUdruzenje.service.IWriterService;

import javax.annotation.PostConstruct;

@Component
public class ServiceHelper {

    @Autowired
    private IUserService userService;

    @Autowired
    private IWriterService writerService;

    @Autowired
    private IBookService bookService;

    @Autowired
    private FormService formService;

    @Autowired
    private TaskService taskService;

    private static ServiceHelper instance;

    @PostConstruct
    public void fillInstance() {
        instance = this;
    }

    public static IUserService getUserServivce() {
        return instance.userService;
    }

    public static IBookService getBookService() {
        return instance.bookService;
    }

    public static IWriterService getWriterService() {
        return instance.writerService;
    }

    public static FormService getFormService(){return instance.formService;}

    public static TaskService getTaskService(){return instance.taskService;}
}
