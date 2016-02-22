package dao.impl;

import dao.IUserDAO;
import domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static constants.Constants.*;

/**
 * Created by Andrii_Pinchuk on 2/6/2016.
 */
@Repository
public class UserDAO implements IUserDAO {
    private static final Logger LOG = LoggerFactory.getLogger(UserDAO.class);
    private static final String GET_USER_BY_ID = "SELECT * FROM users WHERE id=?";
    private static final String GET_USERS_BY_NAME = "SELECT * FROM users WHERE name=?";
    private static final String GET_USERS_BY_EMAIL = "SELECT * FROM users WHERE email=?";
    private static final String DELETE_USER = "DELETE FROM users WHERE id=?";

    private JdbcTemplate jdbcTemplate;

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }
    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public User register(User user) {
        LOG.debug(String.format("Create user start. User - %s", user));
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName(USERS_TABLE_NAME).usingGeneratedKeyColumns(
                PRIMARY_KEY);
        Map<String, Object> parameters = new HashMap<>();
        parameters.put(USERS_COLUMN_EMAIL, user.getEmail());
        parameters.put(USERS_COLUMN_NAME, user.getName());
        parameters.put(USERS_COLUMN_BIRTHDAY, user.getBirthday());
        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(
                parameters));
        user.setId(((Number) key).intValue());
        LOG.debug("Create user end. Result = " + user);
        return user;
    }


    public boolean remove(long userId) {
        LOG.debug(String.format("Delete user start. User_id = %d", userId));
        Object[] args = new Object[]{userId};
        boolean result = jdbcTemplate.update(DELETE_USER, args) > 0;
        LOG.debug("Delete user end. Result = " + result);
        return result;
    }

    public User getById(long userId) {
        LOG.debug("Get user by ID start. ID = " + userId);
        Object[] args = new Object[]{userId};
        User result = jdbcTemplate.queryForObject(GET_USER_BY_ID, args, new UserMapper());
        LOG.debug("Get user by ID end. Result = " + result);
        return result;
    }

    public User getUserByEmail(String email) {
        LOG.debug("Get user by email  start. Email = " + email);
        Object[] args = new Object[]{email};
        User result = jdbcTemplate.queryForObject(GET_USERS_BY_EMAIL, args, new UserMapper());
        LOG.debug("Get user by email  end. Result = " + result);
        return result;
    }

    public List<User> getUsersByName(String name) {
        LOG.debug(String.format("Get users by name start. Name = %s, pageSize = %d, pageNum = %d", name));
        Object[] args = new Object[]{name};
        List<User> result = jdbcTemplate.query(GET_USERS_BY_NAME, args, new UserMapper());
        LOG.debug("Get users by name end. Result = " + result);
        return result;
    }

    public class UserMapper implements RowMapper<User> {
        @Override
        public User mapRow(ResultSet resultSet, int i) throws SQLException {
            User user = new User();
            user.setId(resultSet.getLong(USERS_COLUMN_ID));
            user.setName(resultSet.getString(USERS_COLUMN_NAME));
            user.setEmail(resultSet.getString(USERS_COLUMN_EMAIL));
            user.setBirthday(resultSet.getDate(USERS_COLUMN_BIRTHDAY));
            return user;
        }
    }
}
