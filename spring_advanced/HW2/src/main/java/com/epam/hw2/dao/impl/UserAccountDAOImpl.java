package com.epam.hw2.dao.impl;

import com.epam.hw2.constants.Constants;
import com.epam.hw2.dao.UserAccountDAO;
import com.epam.hw2.model.impl.UserAccountImpl;
import com.epam.hw2.model.UserAccount;
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
import java.util.Map;

/**
 * Created by Andrii_Pinchuk on 11/23/2015.
 */
@Repository("userAccountDAO")
public class UserAccountDAOImpl implements UserAccountDAO {
    private static final Logger LOG = LoggerFactory.getLogger(UserAccountDAOImpl.class);
    private static final String GET_USER_ACCOUNT_BY_USER_ID = "SELECT * FROM user_accounts WHERE user_id=?";
    private static final String DELETE_USER_ACCOUNT_BY_ID = "DELETE FROM user_accounts WHERE id=?";
    private static final String UPDATE_USER_ACCOUNT_BY_ID = "UPDATE user_accounts SET money=? WHERE id=?";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public UserAccount createUserAccount(UserAccount userAccount) {
        LOG.debug(String.format("Create user account start. UserAccount - %s", userAccount));
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName(Constants.USER_ACCOUNTS_TABLE_NAME).usingGeneratedKeyColumns(
                Constants.PRIMARY_KEY);
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put(Constants.USER_ACCOUNTS_COLUMN_MONEY, userAccount.getPrepaidMoney());
        parameters.put(Constants.USER_ACCOUNTS_COLUMN_USER_ID, userAccount.getUserId());
        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(
                parameters));
        userAccount.setId(((Number) key).intValue());
        LOG.debug("Create user account end. Result = " + userAccount);
        return userAccount;
    }

    @Override
    public boolean deleteUserAccount(long userAccountId) {
        LOG.debug(String.format("Delete user account start. User_account_id = %d", userAccountId));
        Object[] args = new Object[]{userAccountId};
        boolean result = jdbcTemplate.update(DELETE_USER_ACCOUNT_BY_ID, args) > 0;
        LOG.debug("Delete user account end");
        return result;
    }

    @Override
    public UserAccount getUserAccountByUserId(long id) {
        LOG.debug("Get user account by ID start. ID = " + id);
        Object[] args = new Object[]{id};
        UserAccount result = jdbcTemplate.queryForObject(GET_USER_ACCOUNT_BY_USER_ID, args, new UserAccountMapper());
        LOG.debug("Get user account by ID end. Result = " + result);
        return result;
    }

    @Override
    public UserAccount updateUserAccount(UserAccount currentUserAccount) {
        LOG.debug("Update user account start. ID = " + currentUserAccount.getId());
        Object[] args = new Object[]{currentUserAccount.getPrepaidMoney(), currentUserAccount.getId()};
        int updatedRows = jdbcTemplate.update(UPDATE_USER_ACCOUNT_BY_ID, args);
        LOG.debug("Update user account end.");
        return updatedRows > 0 ? currentUserAccount : null;
    }

    private class UserAccountMapper implements RowMapper<UserAccount> {
        @Override
        public UserAccount mapRow(ResultSet resultSet, int i) throws SQLException {
            UserAccount userAccount = new UserAccountImpl();
            userAccount.setId(resultSet.getLong(Constants.USER_ACCOUNTS_COLUMN_ID));
            userAccount.setPrepaidMoney(resultSet.getInt(Constants.USER_ACCOUNTS_COLUMN_MONEY));
            userAccount.setUserId(resultSet.getLong(Constants.USER_ACCOUNTS_COLUMN_USER_ID));
            return userAccount;
        }
    }
}
