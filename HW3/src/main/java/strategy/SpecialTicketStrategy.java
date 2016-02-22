package strategy;

import domain.Ticket;
import domain.User;


import java.util.List;

/**
 * Created by Andrii_Pinchuk on 2/7/2016.
 */
public class SpecialTicketStrategy extends DiscountStrategy {
    private int numberOfLuckyTicket;

    public void setNumberOfLuckyTicket(int numberOfLuckyTicket) {
        this.numberOfLuckyTicket = numberOfLuckyTicket;
    }

    @Override
    public int execute(User user, List<Ticket> tickets) {
        return (isLuckyTicket(tickets)) ? discount : 0;
    }

    private boolean isLuckyTicket(List<Ticket> tickets) {
        return (tickets.size() + 1 % numberOfLuckyTicket) == 0 ? true : false;
    }
}
