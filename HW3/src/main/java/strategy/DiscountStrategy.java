package strategy;

import domain.Ticket;
import domain.User;


import java.util.List;

/**
 * Created by Andrii_Pinchuk on 2/7/2016.
 */
public abstract class DiscountStrategy {
    protected int discount;

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public abstract int execute(User user, List<Ticket> tickets);
}
