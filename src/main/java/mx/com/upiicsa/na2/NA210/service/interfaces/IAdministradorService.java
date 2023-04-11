package mx.com.upiicsa.na2.NA210.service.interfaces;

import mx.com.upiicsa.na2.NA210.model.entity.AdministradorModel;
import mx.com.upiicsa.na2.NA210.response.AdministradorRespuesta;

import java.util.Optional;

public interface IAdministradorService {
    AdministradorModel crearAdministrador(AdministradorModel administradorModel);

    AdministradorRespuesta obtenerAdministradores(int numeroPagina, int sizePagina);

    Optional<AdministradorModel> obtenerAdministradorById(long id);
    void actualizarAdministrador(AdministradorModel administradorModel);

    void eliminiarAdministrador(long id);
}
