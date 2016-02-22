package dao.impl;

import dao.IAuditoriumDAO;
import domain.Auditorium;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static constants.Constants.*;

/**
 * Created by Andrii_Pinchuk on 2/6/2016.
 */
@Repository
public class AuditoriumDAO implements IAuditoriumDAO {
    private static final Logger LOG = LoggerFactory.getLogger(AuditoriumDAO.class);
    private static final String GET_ALL = "SELECT * FROM auditoriums";
    private static final String GET_AUDITORIUM_BY_ID = "SELECT * FROM auditoriums WHERE id=?";
    private JdbcTemplate jdbcTemplate;

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Auditorium> getAuditoriums() {
        LOG.debug(String.format("Get all auditoriums.Start."));
        Object[] args = new Object[]{};
        List<Auditorium> result = jdbcTemplate.query(GET_ALL, args, new AuditoriumMapper());
        LOG.debug("Get all auditoriums end. Result = " + result);
        return result;
    }

    @Override
    public long getSeatsNumber(long auditoriumId) {
        Auditorium auditorium = getById(auditoriumId);
        return auditorium.getNumber_of_seats();
    }

    @Override
    public List<Long> getVipSeats(long auditoriumId) {
        Auditorium auditorium = getById(auditoriumId);
        return auditorium.getVip_seats();
    }

    @Override
    public Auditorium getById(long auditoriumId) {
        LOG.debug("Get auditoriums by ID start. ID = " + auditoriumId);
        Object[] args = new Object[]{auditoriumId};
        Auditorium result = jdbcTemplate.queryForObject(GET_AUDITORIUM_BY_ID, args, new AuditoriumMapper());
        LOG.debug("Get user by ID end. Result = " + result);
        return result;
    }

    public class AuditoriumMapper implements RowMapper<Auditorium> {
        @Override
        public Auditorium mapRow(ResultSet resultSet, int i) throws SQLException {
            Auditorium auditorium = new Auditorium();
            auditorium.setId(resultSet.getLong(AUDITORIUMS_COLUMN_ID));
            auditorium.setName(resultSet.getString(AUDITORIUMS_COLUMN_NAME));
            auditorium.setNumber_of_seats(resultSet.getLong(AUDITORIUMS_COLUMN_NUMBER_OF_SEATS));
            List<Long> list = Arrays.asList(resultSet.getString(AUDITORIUMS_COLUMN_VIP_SEATS).split(",")).stream().map(s -> Long.parseLong(s)).collect(Collectors.toList());
            auditorium.setVip_seats(list);
            return auditorium;
        }
    }
}
