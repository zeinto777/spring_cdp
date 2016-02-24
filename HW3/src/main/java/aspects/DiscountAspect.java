package aspects;

import dao.impl.DiscountAspectDAO;
import domain.Ticket;
import domain.User;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

import static constants.Constants.BIRTHDAY;

/**
 * Created by Andrii_Pinchuk on 2/16/2016.
 */
@Aspect
@Component
public class DiscountAspect {
    @Autowired
    DiscountAspectDAO discountAspectDAO;

    @After("execution(* strategy.BirthdayStrategy.execute(..))")
    public void adviceUserDiscount(JoinPoint joinPoint) {
        User user = (User) joinPoint.getArgs()[0];
        discountAspectDAO.update(BIRTHDAY, user.getId());
    }
}
