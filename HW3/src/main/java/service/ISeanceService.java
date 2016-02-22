package service;

import domain.Auditorium;
import domain.Event;
import domain.Seance;
import org.joda.time.DateTime;

import java.util.Date;
import java.util.List;

/**
 * Created by Andrii_Pinchuk on 2/6/2016.
 */
public interface ISeanceService {

    boolean create(Seance seance);

    boolean remove(long seanceId);

    List<Seance> getAll();
}
