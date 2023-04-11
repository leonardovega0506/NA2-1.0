package mx.com.upiicsa.na2.NA210.service.interfaces;



import mx.com.upiicsa.na2.NA210.model.entity.TurnosModel;

import java.util.List;
import java.util.Optional;

public interface ITurnoService {

    TurnosModel createTurno(TurnosModel turnosModel);
    List<TurnosModel> findAllTurnos();

    Optional<TurnosModel> findTurnoById(long id_turno);

    void updateTurno(TurnosModel turnosModel);

    void deleteTurno(long id_turno);
}
