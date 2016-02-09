package dao.impl;

import dao.ISeanceDAO;
import domain.Auditorium;
import domain.Event;
import domain.Seance;
import org.joda.time.DateTime;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Andrii_Pinchuk on 2/6/2016.
 */
@Repository
public class SeanceDAO implements ISeanceDAO {
    private List<Seance> seances = new ArrayList<>();

    @Override
    public boolean create(Seance seance) {
        return seances.add(seance);
    }

    @Override
    public boolean remove(long seanceId) {
        return seances.removeIf(seance -> seance.getId() == seanceId);
    }

    @Override
    public List<Seance> getAll() {
        return seances;
    }
}
