package dao;

import domain.Auditorium;

import java.util.List;

/**
 * Created by Andrii_Pinchuk on 2/6/2016.
 */
public interface IAuditoriumDAO {
    List<Auditorium> getAuditoriums();

    long getSeatsNumber(long auditoriumId);

    List<Long> getVipSeats(long auditoriumId);

    Auditorium getById(long auditoriumId);
}
