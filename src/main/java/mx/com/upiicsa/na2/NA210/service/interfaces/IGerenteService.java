package mx.com.upiicsa.na2.NA210.service.interfaces;

import mx.com.upiicsa.na2.NA210.model.entity.GerenteModel;
import mx.com.upiicsa.na2.NA210.response.GerenteRespuesta;

import java.util.Optional;

public interface IGerenteService {
    GerenteModel crearGerente(GerenteModel gerenteModel);

    GerenteRespuesta obtenerGerentes(int numeroPagina, int sizePagina);

    Optional<GerenteModel> obtenerGerenteByID(long numero_gerente);

    void actualizarGerente(GerenteModel gerenteDTO);

    void eliminarGerente(long numero_gerente);
}
