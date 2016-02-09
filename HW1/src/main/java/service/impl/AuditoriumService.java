package service.impl;

import dao.IAuditoriumDAO;
import domain.Auditorium;
import handler.JSONHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import service.IAuditoriumService;

import java.util.List;

/**
 * Created by Andrii_Pinchuk on 2/6/2016.
 */
@Service("auditoriumService")
public class AuditoriumService implements IAuditoriumService {
    @Autowired
    private IAuditoriumDAO auditoriumDAO;

    @Override
    public List<Auditorium> getAuditoriums() {
        return auditoriumDAO.getAuditoriums();
    }

    @Override
    public long getSeatsNumber(long auditoriumId) {
        return auditoriumDAO.getSeatsNumber(auditoriumId);
    }

    @Override
    public List<Long> getVipSeats(long auditoriumId) {
        return auditoriumDAO.getVipSeats(auditoriumId);
    }

    @Override
    public Auditorium getById(long auditoriumId) {
        return auditoriumDAO.getById(auditoriumId);
    }


}
