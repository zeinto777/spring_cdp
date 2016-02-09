package dao.impl;

import dao.IUserDAO;
import domain.Ticket;
import domain.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Andrii_Pinchuk on 2/6/2016.
 */
@Repository
public class UserDAO implements IUserDAO {
    private List<User> users = new ArrayList<>();

    public boolean register(User user) {
        return users.add(user);
    }

    public boolean remove(long userId) {
        return users.removeIf(user -> user.getId() == userId);
    }

    public User getById(long userId) {
        User result = null;
        Iterator<User> it = users.stream().filter((user) -> user.getId() == userId).iterator();
        if (it.hasNext()) result = it.next();
        return result;
    }

    public User getUserByEmail(String email) {
        User result = null;
        Iterator<User> it = users.stream().filter((user) -> user.getEmail().equals(email)).iterator();
        if (it.hasNext()) result = it.next();
        return result;
    }

    public List<User> getUsersByName(String name) {
        List<User> result = users.stream().filter((user) -> user.getName().equals(name)).collect(Collectors.toList());
        return result;
    }

}
