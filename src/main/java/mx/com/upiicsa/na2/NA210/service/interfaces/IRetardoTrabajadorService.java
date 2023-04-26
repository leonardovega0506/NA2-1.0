package mx.com.upiicsa.na2.NA210.service.interfaces;



import mx.com.upiicsa.na2.NA210.model.entity.RenunciaTrabajadorModel;
import mx.com.upiicsa.na2.NA210.model.entity.RetradoTrabajadorModel;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface IRetardoTrabajadorService {

    RetradoTrabajadorModel createRetardo(long id_trabajador, RetradoTrabajadorModel retradoTrabajadorModel);

    List<RetradoTrabajadorModel> findRetardosTrabajadorID(long id_trabajador);

    Optional<RetradoTrabajadorModel> findRetardoById(long id_retardo);

    List<RetradoTrabajadorModel> findAllRetardos();

    List<RetradoTrabajadorModel> findARetardosByDate(LocalDate fecha);
}
