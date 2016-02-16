package dao;

import domain.Ticket;
import domain.User;

import java.util.List;

/**
 * Created by Andrii_Pinchuk on 2/6/2016.
 */
public interface IUserDAO {

    boolean register(User user);

    boolean remove(long userId);

    User getById(long userId);

    User getUserByEmail(String email);

    List<User> getUsersByName(String name);
}
