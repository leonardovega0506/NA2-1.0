package mx.com.upiicsa.na2.NA210.service.interfaces;


import mx.com.upiicsa.na2.NA210.model.entity.TrabajadorModel;
import mx.com.upiicsa.na2.NA210.response.TrabajadorRespuesta;

import java.util.Optional;

public interface ITrabajadorService {

    TrabajadorModel crearTrabajador(TrabajadorModel trabajadorModel);

    TrabajadorRespuesta obtenerTrabajadores(int numeroPagina, int sizePagina);

    Optional<TrabajadorModel> obtenerTrabajadorById(long id_trabajador);

    void actualizarTrabajador(TrabajadorModel trabajadorModel);

    void eliminarTrabajador(long numero_trabajador);
}
