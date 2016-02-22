package aspects;

import domain.Event;
import domain.Ticket;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import service.IEventService;

/**
 * Created by Andrii_Pinchuk on 2/16/2016.
 */
@Aspect
@Component
public class LuckyWinnerAspect {
    private static float randomFactor;

    @Autowired
    private IEventService eventService;

    public void setRandomFactor(float randomFactor) {
        this.randomFactor = randomFactor;
    }

    @Around("execution(* service.IBookingService.bookTicket(..)) && args(ticket) ")
    public Ticket adviceGetBookTicket(ProceedingJoinPoint proceedingJoinPoint, Ticket ticket) throws Throwable {
        Event event = eventService.getById(ticket.getEventId());
        if (randomBoolean()) event.setTicketPrice(0);
        return (Ticket) proceedingJoinPoint.proceed(new Object[]{ticket});
    }

    private boolean randomBoolean() {
        return Math.random() < randomFactor;
    }
}
