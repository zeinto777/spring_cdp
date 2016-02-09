package service.impl;

import domain.Ticket;
import domain.User;
import org.springframework.stereotype.Service;
import service.IDiscountService;
import strategy.DiscountStrategy;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andrii_Pinchuk on 2/7/2016.
 */
@Service("discountService")
public class DiscountService implements IDiscountService {

    private List<DiscountStrategy> discountStrategies = new ArrayList<>();

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
