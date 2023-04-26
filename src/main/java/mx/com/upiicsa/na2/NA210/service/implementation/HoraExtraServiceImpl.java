package mx.com.upiicsa.na2.NA210.service.implementation;

import mx.com.upiicsa.na2.NA210.model.entity.HoraExtraModel;
import mx.com.upiicsa.na2.NA210.model.entity.TrabajadorModel;
import mx.com.upiicsa.na2.NA210.repository.IHoraExtraRepository;
import mx.com.upiicsa.na2.NA210.repository.ITrabajadorRepository;
import mx.com.upiicsa.na2.NA210.service.interfaces.IHoraExtraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class HoraExtraServiceImpl implements IHoraExtraService {


    @Autowired
    private ITrabajadorRepository iTrabajador;

    @Autowired
    private IHoraExtraRepository iHoraExtra;


    @Override
    public HoraExtraModel createHoraExtra(Long id_trabajador, HoraExtraModel horaExtraModel) {
        Optional<TrabajadorModel> oTrabajador = iTrabajador.findById(id_trabajador);
        horaExtraModel.setAumento_total(horaExtraModel.getCosto_hora()* horaExtraModel.getCantidad_horas());
        horaExtraModel.setTrabajadorModel(oTrabajador.get());
        return iHoraExtra.save(horaExtraModel);
    }

    @Override
    public List<HoraExtraModel>findAllHoraExtraTrabajadorID(long id_trabajador) {
        return iHoraExtra.findByTrabajadorModel_Id(id_trabajador);
    }

    @Override
    public List<HoraExtraModel> findAllHorasExtra() {
        return iHoraExtra.findAll();
    }

    @Override
    public List<HoraExtraModel> findHoraExtraByDate(LocalDate fecha) {
        return iHoraExtra.findByFechaHoraExtra(fecha);
    }

    @Override
    public Optional<HoraExtraModel> findHoraExtra(long id_trabajador, long id_horaExtra) {
        Optional<TrabajadorModel> oTrabajador = iTrabajador.findById(id_trabajador);
        Optional<HoraExtraModel> oHoraExtra = iHoraExtra.findById(id_horaExtra);

        if(oHoraExtra.get().getTrabajadorModel().getId() != oTrabajador.get().getId()){
            return null;
        }

        return oHoraExtra;
    }

}
