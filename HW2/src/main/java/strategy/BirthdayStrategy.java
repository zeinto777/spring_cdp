package strategy;

import domain.Ticket;
import domain.User;
import org.joda.time.DateTime;


import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by Andrii_Pinchuk on 2/7/2016.
 */
public class BirthdayStrategy extends DiscountStrategy {
    @Override
    public int execute(User user, List<Ticket> tickets) {
        return (checkForBirthdayDay(user)) ? discount : 0;
    }

    private boolean checkForBirthdayDay(User user) {
        Date currentDate = GregorianCalendar.getInstance().getTime();
        String today = new DateTime(currentDate).toString("MM-dd");
        String birth = new DateTime(user.getBirthday()).toString("MM-dd");
        return today.equals(birth) ? true : false;
    }
}
