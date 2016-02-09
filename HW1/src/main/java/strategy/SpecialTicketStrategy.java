package strategy;

import domain.Ticket;
import domain.User;


import java.util.List;

/**
 * Created by Andrii_Pinchuk on 2/7/2016.
 */
public class SpecialTicketStrategy implements DiscountStrategy {
    private int NUMBER_OF_LUCKY_TICKET = 10;
    private int DISCOUNT_FOR_LUCKY_TICKET = 50;


    @Override
    public int execute(User user, List<Ticket> tickets) {
        return (isLuckyTicket(tickets)) ? DISCOUNT_FOR_LUCKY_TICKET : 0;
    }

    private boolean isLuckyTicket(List<Ticket> tickets) {
        return (tickets.size() + 1 % NUMBER_OF_LUCKY_TICKET) == 0 ? true : false;
    }
}
