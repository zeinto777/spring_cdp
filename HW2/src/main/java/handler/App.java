package handler;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
//����� ����� ��������� ����?  ����� �� ������� ��������� ���������� ����� ���� �� ���. � ����� �����

/**
 * Created by Andrii_Pinchuk on 11/12/2015.
 */
public class App {


    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring.xml");

    }




}
