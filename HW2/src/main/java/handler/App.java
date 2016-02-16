package handler;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
//зачем нужны внутринее бины?  чтобы не плодить различные определния бинов елси он исп. в одном месте

/**
 * Created by Andrii_Pinchuk on 11/12/2015.
 */
public class App {


    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring.xml");

    }




}
