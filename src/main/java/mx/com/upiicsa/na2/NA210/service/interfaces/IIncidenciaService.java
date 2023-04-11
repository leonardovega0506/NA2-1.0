package mx.com.upiicsa.na2.NA210.service.interfaces;


import mx.com.upiicsa.na2.NA210.model.entity.IncidenciaModel;

import java.util.List;
import java.util.Optional;

public interface IIncidenciaService {

    IncidenciaModel createIncidencia(Long id_trabajador, IncidenciaModel incidenciaDTO);

    List<IncidenciaModel> findAllInciencias(Long id_trabajador);

    Optional<IncidenciaModel> findIncidencia(Long id_trabajador, long id_incidencia);
}
