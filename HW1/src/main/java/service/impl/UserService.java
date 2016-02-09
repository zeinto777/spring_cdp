package service.impl;

import dao.IUserDAO;
import domain.Ticket;
import domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.IBookingService;
import service.IUserService;

import java.util.List;

/**
 * Created by Andrii_Pinchuk on 2/6/2016.
 */

@Service
public class UserService implements IUserService {
    @Autowired
    private IBookingService bookingService;
    @Autowired
    private IUserDAO userDAO;

    public void setUserDAO(IUserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public boolean register(User user) {
        return userDAO.register(user);
    }

    public boolean remove(long userId) {
        return userDAO.remove(userId);
    }

    public User getById(long userId) {
        return userDAO.getById(userId);
    }

    public User getUserByEmail(String email) {
        return userDAO.getUserByEmail(email);
    }

    public List<User> getUsersByName(String name) {
        return userDAO.getUsersByName(name);
    }

    public List<Ticket> getBookedTickets(long userId) {
        return bookingService.getTicketsByUserId(userId);
    }
}
