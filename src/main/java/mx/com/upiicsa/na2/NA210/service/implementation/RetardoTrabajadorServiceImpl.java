package mx.com.upiicsa.na2.NA210.service.implementation;


import mx.com.upiicsa.na2.NA210.model.entity.RetradoTrabajadorModel;
import mx.com.upiicsa.na2.NA210.model.entity.TrabajadorModel;
import mx.com.upiicsa.na2.NA210.repository.IRetardoRepository;
import mx.com.upiicsa.na2.NA210.repository.ITrabajadorRepository;
import mx.com.upiicsa.na2.NA210.service.interfaces.IRetardoTrabajadorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RetardoTrabajadorServiceImpl implements IRetardoTrabajadorService {

    @Autowired
    private IRetardoRepository iRetardo;

    @Autowired
    private ITrabajadorRepository iTrabajador;


    @Override
    public RetradoTrabajadorModel createRetardo(long id_trabajador, RetradoTrabajadorModel retradoTrabajadorModel) {
        Optional<TrabajadorModel> oTrabajador = iTrabajador.findById(id_trabajador);
        retradoTrabajadorModel.setTrabajadorModel(oTrabajador.get());
        return iRetardo.save(retradoTrabajadorModel);
    }

    @Override
    public List<RetradoTrabajadorModel> findRetardosTrabajadorID(long id_trabajador) {
       return iRetardo.findByTrabajadorModel_Id(id_trabajador);
    }
    @Override
    public List<RetradoTrabajadorModel> findAllRetardos() {
        return iRetardo.findAll();
    }

    @Override
    public Optional<RetradoTrabajadorModel> findRetardoById(long id_retardo) {
        return iRetardo.findById(id_retardo);
    }
}
