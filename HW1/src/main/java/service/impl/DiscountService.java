package service.impl;

import domain.Ticket;
import domain.User;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Service;
import service.IDiscountService;
import strategy.DiscountStrategy;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andrii_Pinchuk on 2/7/2016.
 */

public class DiscountService implements IDiscountService {

    private List<DiscountStrategy> discountStrategies;

    @Required
    public void setDiscountStrategies(List<DiscountStrategy> discountStrategies) {
        this.discountStrategies = discountStrategies;
    }

    public int getDiscount(User user, List<Ticket> tickets) {
        int result = 0;
        for (DiscountStrategy discountStrategy : discountStrategies) {
            if (result < discountStrategy.execute(user, tickets)) {
                result = discountStrategy.execute(user, tickets);
            }
        }
        return result;
    }
}
