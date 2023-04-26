package mx.com.upiicsa.na2.NA210.service.implementation;

import mx.com.upiicsa.na2.NA210.model.entity.TareaModel;
import mx.com.upiicsa.na2.NA210.model.entity.TrabajadorModel;
import mx.com.upiicsa.na2.NA210.repository.ITareaRepository;
import mx.com.upiicsa.na2.NA210.repository.ITrabajadorRepository;
import mx.com.upiicsa.na2.NA210.service.interfaces.ITareaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TareaServiceImpl implements ITareaService {

    @Autowired
    private ITareaRepository iTarea;

    @Autowired
    private ITrabajadorRepository iTrabajador;


    @Override
    public TareaModel createTarea(long id_trabajador, TareaModel tareaModel) {
        Optional<TrabajadorModel> oTrabajador = iTrabajador.findById(id_trabajador);
        if (oTrabajador.get().getEstatus().equals("Ocupado") || oTrabajador.get().getEstatus().equals("En Vacaciones") || oTrabajador.get().getEstatus().equals("Pendiende de Renuncia") || oTrabajador.get().getEstatus().equals("Dehabilitado")) {
            return null;
        } else {
            TrabajadorModel trabajadorAsignado = oTrabajador.get();
            trabajadorAsignado.setEstatus("Ocupado");
            iTrabajador.save(trabajadorAsignado);
            tareaModel.setTrabajadorModel(trabajadorAsignado);
            return iTarea.save(tareaModel);
        }

    }

    @Override
    public List<TareaModel> findTareasTrabajador(long id_trabjador) {
        return iTarea.findByTrabajadorModel_Id(id_trabjador);
    }

    @Override
    public List<TareaModel> findAllTareas() {
        return iTarea.findAll();
    }

    @Override
    public List<TareaModel> findTareaByDate(LocalDate fecha) {
        return iTarea.findByFecha(fecha);
    }

    @Override
    public Optional<TareaModel> findTareaByID(long id_tarea) {
        return iTarea.findById(id_tarea);
    }

    @Override
    public void updateTarea(long id_trabajador, TareaModel tareaModel) {
        Optional<TrabajadorModel> oTrabajador = iTrabajador.findById(id_trabajador);
            TrabajadorModel trabajadorAsignado = oTrabajador.get();
            iTrabajador.save(trabajadorAsignado);
            tareaModel.setTrabajadorModel(trabajadorAsignado);
             iTarea.save(tareaModel);

    }
}
