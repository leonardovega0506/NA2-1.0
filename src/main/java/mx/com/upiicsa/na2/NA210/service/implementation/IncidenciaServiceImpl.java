package mx.com.upiicsa.na2.NA210.service.implementation;

import mx.com.upiicsa.na2.NA210.model.entity.IncidenciaModel;
import mx.com.upiicsa.na2.NA210.model.entity.TrabajadorModel;
import mx.com.upiicsa.na2.NA210.repository.IIncidenciaRepository;
import mx.com.upiicsa.na2.NA210.repository.ITrabajadorRepository;
import mx.com.upiicsa.na2.NA210.service.interfaces.IIncidenciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class IncidenciaServiceImpl implements IIncidenciaService {

    @Autowired
    private ITrabajadorRepository iTrabajador;

    @Autowired
    private IIncidenciaRepository iIncidencia;



    @Override
    public IncidenciaModel createIncidencia(Long id_trabajador, IncidenciaModel incidenciaDTO) {
        Optional<TrabajadorModel> oTrabajador = iTrabajador.findById(id_trabajador);
        incidenciaDTO.setTrabajadorModel(oTrabajador.get());
        return iIncidencia.save(incidenciaDTO);
    }

    @Override
    public List<IncidenciaModel> findAllInciencias(Long id_trabajador) {
        return iIncidencia.findByTrabajadorModel_Id(id_trabajador);
    }

    @Override
    public Optional<IncidenciaModel> findIncidencia(Long id_trabajador, long id_incidencia) {
        Optional<IncidenciaModel> oInicidencia = iIncidencia.findById(id_incidencia);
        Optional<TrabajadorModel> oTrabajador = iTrabajador.findById(id_trabajador);

        if(oInicidencia.get().getTrabajadorModel().getId()!= oTrabajador.get().getId()){
            return null;
        }
        return oInicidencia;
    }

}