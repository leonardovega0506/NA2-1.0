package mx.com.upiicsa.na2.NA210.service.interfaces;



import mx.com.upiicsa.na2.NA210.model.entity.TareaModel;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ITareaService {
    TareaModel createTarea (long id_trabajador,TareaModel tareaModel);

    List<TareaModel> findTareasTrabajador(long id_trabjador);

    Optional<TareaModel> findTareaByID(long id_tarea);

    void updateTarea(long id_trabajador, TareaModel tareaModel);

    List<TareaModel> findAllTareas();

    List<TareaModel> findTareaByDate(LocalDate fecha);
}
