package mx.com.upiicsa.na2.NA210.service.interfaces;



import mx.com.upiicsa.na2.NA210.model.entity.HoraExtraModel;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface IHoraExtraService {
    HoraExtraModel createHoraExtra(Long id_trabajador, HoraExtraModel horaExtraModel);

    List<HoraExtraModel> findAllHoraExtraTrabajadorID(long id_trabajador);

    Optional<HoraExtraModel> findHoraExtra(long id_trabajador, long id_horaExtra);

    List<HoraExtraModel> findAllHorasExtra();

    List<HoraExtraModel> findHoraExtraByDate(LocalDate fecha);
}
