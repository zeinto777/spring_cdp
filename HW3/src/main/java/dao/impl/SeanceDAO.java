package dao.impl;

import dao.ISeanceDAO;
import domain.Seance;
import org.joda.time.DateTime;
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
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static constants.Constants.*;

/**
 * Created by Andrii_Pinchuk on 2/6/2016.
 */
@Repository("seance")
public class SeanceDAO implements ISeanceDAO {
    private static final Logger LOG = LoggerFactory.getLogger(SeanceDAO.class);
    private static final String DELETE_SEANCE = "DELETE FROM seances WHERE id=?";
    private static final String GET_ALL = "SELECT * FROM seances";
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public boolean create(Seance seance) {
        LOG.debug(String.format("Create seance start. Seance - %s", seance));
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName(SEANCES_TABLE_NAME).usingGeneratedKeyColumns(
                PRIMARY_KEY);
        Map<String, Object> parameters = new HashMap<>();
        parameters.put(SEANCES_COLUMN_EVENT_ID, seance.getEventId());
        parameters.put(SEANCES_COLUMN_AUDITORIUM_ID, seance.getAuditoriumId());
        parameters.put(SEANCES_COLUMN_DATETIME, new Timestamp(seance.getDateTime().getMillis()));
        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(
                parameters));
        seance.setId((key).intValue());
        LOG.debug("Create seance end. Result = " + seance);
        return true;
    }

    @Override
    public boolean remove(long seanceId) {
        LOG.debug(String.format("Delete seance start. Seance_id = %d", seanceId));
        Object[] args = new Object[]{seanceId};
        boolean result = jdbcTemplate.update(DELETE_SEANCE, args) > 0;
        LOG.debug("Delete seance end. Result = " + result);
        return result;
    }

    @Override
    public List<Seance> getAll() {
        LOG.debug(String.format("Get all seances.Start."));
        Object[] args = new Object[]{};
        List<Seance> result = jdbcTemplate.query(GET_ALL, args, new SeanceMapper());
        LOG.debug("Get all seances end. Result = " + result);
        return result;
    }


    public class SeanceMapper implements RowMapper<Seance> {
        @Override
        public Seance mapRow(ResultSet resultSet, int i) throws SQLException {
            Seance seance = new Seance();
            seance.setId(resultSet.getLong(SEANCES_COLUMN_ID));
            seance.setEventId(resultSet.getLong(SEANCES_COLUMN_EVENT_ID));
            seance.setAuditoriumId(resultSet.getLong(SEANCES_COLUMN_AUDITORIUM_ID));
            seance.setDateTime(new DateTime(resultSet.getTimestamp(SEANCES_COLUMN_DATETIME)));
            return seance;
        }
    }
}
