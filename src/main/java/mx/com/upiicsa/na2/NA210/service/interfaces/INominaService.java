package mx.com.upiicsa.na2.NA210.service.interfaces;


import mx.com.upiicsa.na2.NA210.model.entity.NominaTrabajadorModel;

import java.util.List;
import java.util.Optional;

public interface INominaService {
    NominaTrabajadorModel createNomina(long id_trabajador);

    List<NominaTrabajadorModel> findAllNominasTrabajador(long id_trabajador);

    Optional<NominaTrabajadorModel> findNominaByID(long id_trabajador, long id_nomina);

    void updateNomina(long id_trabajador, NominaTrabajadorModel nominaTrabajadorModel);

    List<NominaTrabajadorModel> findAllNominas();
}
