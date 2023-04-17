package mx.com.upiicsa.na2.NA210.service.implementation;

import mx.com.upiicsa.na2.NA210.repository.ITurnoRepository;
import mx.com.upiicsa.na2.NA210.service.interfaces.ITurnoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TurnoServiceImpl implements ITurnoService {


    @Autowired
    private ITurnoRepository iTurno;

    @Override
    public TurnosModel createTurno(TurnosModel turnosModel) {
        return iTurno.save(turnosModel);
    }

    @Override
    public List<TurnosModel> findAllTurnos() {
        return iTurno.findAll();
    }

    @Override
    public Optional<TurnosModel> findTurnoById(long id_turno) {
        return iTurno.findById(id_turno);
    }

    @Override
    public void updateTurno(TurnosModel turnosModel) {
        iTurno.save(turnosModel);
    }

    @Override
    public void deleteTurno(long id_turno) {
        iTurno.deleteById(id_turno);
    }

}
