import domain.Auditorium;
import handler.JSONHandler;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import service.IAuditoriumService;
import service.impl.AuditoriumService;

/**
 * Created by Andrii_Pinchuk on 11/12/2015.
 */
public class App {

    private static ConfigurableApplicationContext applicationContext;

    public static void main(String[] args) {
        applicationContext = new ClassPathXmlApplicationContext("spring.xml");
        IAuditoriumService auditoriumService = (IAuditoriumService) applicationContext.getBean("auditoriumService");
        auditoriumService.getAuditoriums();
        applicationContext.close();
    }


}
