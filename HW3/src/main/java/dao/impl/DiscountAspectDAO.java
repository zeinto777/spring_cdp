package dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * Created by Andrii_Pinchuk on 2/24/2016.
 */
@Repository
public class DiscountAspectDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private static final String UPDATE_EVENT = "UPDATE user_counter as t1,(SELECT * FROM user_counter WHERE userId=%d) as t2 SET t1.%s=t2.%s+1 WHERE t1.userId=%d";
    private static final String INSERT_EVENT = "INSERT INTO `user_counter`(`eventId`, `birthday`) values(?,0)";

    public void update(String eventName, Long userId) {
        String query = String.format(UPDATE_EVENT, userId, eventName, eventName, userId);
        try {
            jdbcTemplate.update(query, new Object[]{});
        } catch (EmptyResultDataAccessException e) {
            jdbcTemplate.update(INSERT_EVENT, new Object[]{userId});
        }
    }
}
