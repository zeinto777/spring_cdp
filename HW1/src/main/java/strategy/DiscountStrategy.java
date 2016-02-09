package strategy;

import domain.Ticket;
import domain.User;


import java.util.List;

/**
 * Created by Andrii_Pinchuk on 2/7/2016.
 */
public interface DiscountStrategy {

    int execute(User user, List<Ticket> tickets);
}
