package mx.com.upiicsa.na2.NA210.service.interfaces;


import lombok.Setter;
import mx.com.upiicsa.na2.NA210.model.entity.NominaTrabajadorModel;
import mx.com.upiicsa.na2.NA210.service.implementation.NominaServiceImpl;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface INominaService {
    NominaTrabajadorModel createNomina(long id_trabajador);

    List<NominaTrabajadorModel> findAllNominasTrabajador(Long id_trabajador);

    Optional<NominaTrabajadorModel> findNominaByID(long id_trabajador, long id_nomina);

    void updateNomina(long id_trabajador, NominaTrabajadorModel nominaTrabajadorModel);

    List<NominaTrabajadorModel> findAllNominas();

    List<NominaTrabajadorModel> findAllNominasByFecha(LocalDate fecha);

    List<NominaTrabajadorModel> findNominasByDate(LocalDate fecha);
}
