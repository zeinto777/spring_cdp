package dao.impl;

import dao.IAuditoriumDAO;
import domain.Auditorium;
import domain.Event;
import domain.Seance;
import domain.User;
import handler.JSONHandler;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Andrii_Pinchuk on 2/6/2016.
 */
@Repository
public class AuditoriumDAO implements IAuditoriumDAO {
    private List<Auditorium> auditoriums;

    @Autowired
    private JSONHandler jsonHandler;

    @PostConstruct
    public void init() {
        jsonHandler.parse();
        auditoriums = new ArrayList<>(jsonHandler.getCommonStorage().values());
    }

    @Override
    public List<Auditorium> getAuditoriums() {
        return auditoriums;
    }

    @Override
    public long getSeatsNumber(long auditoriumId) {
        return auditoriums.get((int) auditoriumId).getNumber_of_seats();
    }

    @Override
    public List<Long> getVipSeats(long auditoriumId) {
        return auditoriums.get((int) auditoriumId).getVip_seats();
    }

    @Override
    public Auditorium getById(long userId) {
        Auditorium result = null;
        Iterator<Auditorium> it = auditoriums.stream().filter((auditorium) -> auditorium.getId() == userId).iterator();
        if (it.hasNext()) result = it.next();
        return result;
    }

}
