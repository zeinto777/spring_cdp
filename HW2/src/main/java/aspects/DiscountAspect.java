package aspects;

import domain.Ticket;
import domain.User;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
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
    private Map<Long, Map<String, Integer>> counters = new HashMap<>();

    @After("execution(* strategy.BirthdayStrategy.execute(..))")
    public void adviceUserDiscount(JoinPoint joinPoint) {
        User user = (User) joinPoint.getArgs()[0];
        Map<String, Integer> associatedEvents;
        associatedEvents = !counters.containsKey(user.getId()) ? new HashMap<>() : counters.get(user.getId());
        Integer count = associatedEvents.containsKey(BIRTHDAY) ? associatedEvents.get(BIRTHDAY) + 1 : 0;
        associatedEvents.put(BIRTHDAY, count);
        counters.put(user.getId(), associatedEvents);
    }


}
