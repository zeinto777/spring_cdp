package com.epam.hw1.dao.impl;

import com.epam.hw1.constants.Constants;
import com.epam.hw1.dao.TicketDAO;
import com.epam.hw1.dao.UserAccountDAO;
import com.epam.hw1.dao.UserDAO;
import com.epam.hw1.exception.BalanceException;
import com.epam.hw1.model.Event;
import com.epam.hw1.model.Ticket;
import com.epam.hw1.model.User;
import com.epam.hw1.model.UserAccount;
import com.epam.hw1.model.impl.TicketImpl;
import com.epam.hw1.source.CustomSqlParameterSource;
import com.epam.hw1.dao.EventDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Andrii_Pinchuk on 11/17/2015.
 */
@Repository("ticketDAO")
public class TicketDAOImpl implements TicketDAO {
    private static final Logger LOG = LoggerFactory.getLogger(TicketDAOImpl.class);

    private static final String GET_TICKET_BY_ID = "SELECT * FROM tickets WHERE id=?";
    private static final String DELETE_TICKET_BY_ID = "DELETE FROM tickets WHERE id=?";
    private static final String GET_TICKETS_BY_USER = "SELECT * FROM tickets INNER JOIN events " +
            " ON tickets.event_id = events.id " +
            " WHERE user_id=:user_id ORDER BY events.date LIMIT :pageNum,:pageSize";
    private static final String GET_TICKETS_BY_EVENT = "SELECT * FROM tickets INNER JOIN users " +
            " ON tickets.user_id = users.id " +
            " WHERE event_id=? ORDER BY users.email LIMIT ?,?";
    private static final String CREATE_TICKET = "INSERT INTO tickets (place, event_id, user_id, category_id) VALUES (?, ?, ?, ?)";

    @Autowired
    private EventDAO eventDAO;
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private UserAccountDAO userAccountDAO;
    @Autowired
    private PlatformTransactionManager transactionManager;

    @Autowired
    private CustomSqlParameterSource customSqlParameterSource;

    public void setCustomSqlParameterSource(CustomSqlParameterSource customSqlParameterSource) {
        this.customSqlParameterSource = customSqlParameterSource;
    }

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public PlatformTransactionManager getTransactionManager() {
        return transactionManager;
    }

    @Autowired
    public void setTransactionManager(PlatformTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    public void setEventDAO(EventDAO eventDAO) {
        this.eventDAO = eventDAO;
    }

    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public void setUserAccountDAO(UserAccountDAO userAccountDAO) {
        this.userAccountDAO = userAccountDAO;
    }

    @Override
    @Transactional
    public Ticket bookTicket(Ticket ticket) {
        LOG.debug(String.format("Book ticket start. UserId = %d, eventId = %d, place = %d, category = %s", ticket.getUserId(), ticket.getEventId(), ticket.getPlace(), ticket.getCategory()));
        int ticketPrice = eventDAO.getEventById(ticket.getEventId()).getTicketPrice();
        int prepaidMoney = userAccountDAO.getUserAccountByUserId(ticket.getUserId()).getPrepaidMoney();
        if ((prepaidMoney - ticketPrice) < 0) {
            LOG.error("User haven't money to buy ticket");
            throw new BalanceException("User haven't money to buy ticket");
        }
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName(Constants.TICKETS_TABLE_NAME).usingGeneratedKeyColumns(
                Constants.PRIMARY_KEY);
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put(Constants.TICKETS_COLUMN_PLACE, ticket.getPlace());
        parameters.put(Constants.TICKETS_COLUMN_EVENT_ID, ticket.getEventId());
        parameters.put(Constants.TICKETS_COLUMN_USER_ID, ticket.getUserId());
        parameters.put(Constants.TICKETS_COLUMN_CATEGORY_ID, ticket.getCategory().ordinal() + 1);
        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(
                parameters));
        ticket.setId(((Number) key).intValue());

        UserAccount userAccount = userAccountDAO.getUserAccountByUserId(ticket.getUserId());
        userAccount.setPrepaidMoney(userAccount.getPrepaidMoney() - eventDAO.getEventById(ticket.getEventId()).getTicketPrice());
        userAccountDAO.updateUserAccount(userAccount);
        LOG.debug("Book ticket end. Result = " + ticket);
        return ticket;
    }

    @Override
    public List<Ticket> getBookedTickets(User user, int pageSize, int pageNum) {
        LOG.info(String.format("Get booked tickets for user start. User id = %s, pageSize = %d, pageNum = %d", user.getId(), pageSize, pageNum));
        customSqlParameterSource.addValue(Constants.TICKETS_COLUMN_USER_ID, user.getId());
        customSqlParameterSource.addValue("pageNum", pageNum);
        customSqlParameterSource.addValue("pageSize", pageSize);
        NamedParameter namedParameter = new NamedParameter();
        namedParameter.setJdbcTemplate(jdbcTemplate);
        List<Ticket> tickets = namedParameter.getNamedParameterJdbcTemplate().query(GET_TICKETS_BY_USER, customSqlParameterSource, new TicketMapper());
        LOG.debug("Get booked tickets for user start. Result = " + tickets);
        return tickets;
    }

    @Override
    public List<Ticket> getBookedTickets(Event event, int pageSize, int pageNum) {
        LOG.debug(String.format("Get booked tickets by event start. Event id = %s, pageSize = %d, pageNum = %d", event.getId(), pageSize, pageNum));
        Object[] args = new Object[]{event.getId(), pageNum, pageSize};
        List<Ticket> result = jdbcTemplate.query(GET_TICKETS_BY_EVENT, args, new TicketMapper());
        LOG.debug("Get booked tickets by event end. Result = " + result);
        return result;
    }

    @Override
    public boolean cancelTicket(long ticketId) {
        LOG.debug(String.format("Delete ticket start. Ticket_id = %d", ticketId));
        Object[] args = new Object[]{ticketId};
        boolean result = jdbcTemplate.update(DELETE_TICKET_BY_ID, args) > 0;
        LOG.debug("Delete ticket end. Result = " + result);
        return result;
    }

    @Override
    public Ticket getTicketById(long id) {
        LOG.debug("Get ticket by ID start. ID = " + id);
        Object[] args = new Object[]{id};
        Ticket result = jdbcTemplate.queryForObject(GET_TICKET_BY_ID, args, new TicketMapper());
        LOG.debug("Get ticket by ID end. Result = " + result);
        return result;
    }

    @Override
    public boolean bookTicketsFromList(List<Ticket> tickets) {
        LOG.debug("Book ticket from list start");
        TransactionDefinition def = new DefaultTransactionDefinition();
        TransactionStatus status = transactionManager.getTransaction(def);
        boolean result = false;
        try {
            List<Object[]> args = new ArrayList<>();
            for (Ticket ticket : tickets) {
                args.add(new Object[]{ticket.getPlace(), ticket.getEventId(), ticket.getUserId(), ticket.getCategory().ordinal() + 1});
            }
            result = jdbcTemplate.batchUpdate(CREATE_TICKET, args).length > 0;
            LOG.debug("Book ticket from list end. Result = " + result);
            transactionManager.commit(status);
        } catch (DataAccessException e) {
            LOG.error(e.getMessage());
            transactionManager.rollback(status);
        }
        return result;
    }

    public class TicketMapper implements RowMapper<Ticket> {
        @Override
        public Ticket mapRow(ResultSet resultSet, int i) throws SQLException {
            Ticket ticket = new TicketImpl();
            ticket.setId(resultSet.getLong(Constants.TICKETS_COLUMN_ID));
            ticket.setPlace(resultSet.getInt(Constants.TICKETS_COLUMN_PLACE));
            ticket.setEventId(resultSet.getInt(Constants.TICKETS_COLUMN_EVENT_ID));
            ticket.setUserId(resultSet.getInt(Constants.TICKETS_COLUMN_USER_ID));
            ticket.setCategory(Ticket.Category.values()[resultSet.getInt(Constants.TICKETS_COLUMN_CATEGORY_ID) - 1]);
            return ticket;
        }
    }

    private class NamedParameter extends NamedParameterJdbcDaoSupport {
    }
}
