package service.impl;

import dao.ISeanceDAO;
import domain.Seance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.ISeanceService;

import java.util.List;

/**
 * Created by Andrii_Pinchuk on 2/6/2016.
 */
@Service
public class SeanceService implements ISeanceService {
    @Autowired
    private ISeanceDAO seanceDAO;

    @Override
    public boolean create(Seance seance) {
        return seanceDAO.create(seance);
    }

    @Override
    public boolean remove(long seanceId) {
        return false;
    }

    @Override
    public List<Seance> getAll() {
        return seanceDAO.getAll();
    }
}
