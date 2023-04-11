package mx.com.upiicsa.na2.NA210.service.interfaces;

import mx.com.upiicsa.na2.NA210.model.entity.VacacionModel;

import java.util.List;
import java.util.Optional;

public interface IVacacionesService {
    VacacionModel createVacacion(long id_trabajador,VacacionModel vacacionModel);

    List<VacacionModel> findAllVacacionesTrabajador(long id_trabajador);

    Optional<VacacionModel> findVacaciones(long id_vacaciones);

    void updateVacaciones(VacacionModel vacacionesModel);

    void deleteVacaciones(long id_vacaciones);

    List<VacacionModel> findAllVacaciones();
}
