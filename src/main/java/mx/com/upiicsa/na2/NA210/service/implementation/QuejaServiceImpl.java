package mx.com.upiicsa.na2.NA210.service.implementation;

import mx.com.upiicsa.na2.NA210.model.entity.QuejasAclaracionesModel;
import mx.com.upiicsa.na2.NA210.model.entity.TrabajadorModel;
import mx.com.upiicsa.na2.NA210.repository.IQuejaAclaracionRepository;
import mx.com.upiicsa.na2.NA210.repository.ITrabajadorRepository;
import mx.com.upiicsa.na2.NA210.service.interfaces.IQuejaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class QuejaServiceImpl implements IQuejaService {

    @Autowired
    private IQuejaAclaracionRepository iQueja;

    @Autowired
    private ITrabajadorRepository iTrabajador;


    @Override
    public QuejasAclaracionesModel createQueja(long id_trabajador, QuejasAclaracionesModel quejasAclaracionesModel) {
        Optional<TrabajadorModel> oTrabajador = iTrabajador.findById(id_trabajador);
        quejasAclaracionesModel.setTrabajadorModel(oTrabajador.get());
        return iQueja.save(quejasAclaracionesModel);
    }

    @Override
    public List<QuejasAclaracionesModel> findAllQuejasTrabajador(long id_trabajador) {
        return iQueja.findByTrabajadorModel_Id(id_trabajador);
    }

    @Override
    public List<QuejasAclaracionesModel> findAllQuejas() {
        return iQueja.findAll();
    }

    @Override
    public Optional<QuejasAclaracionesModel> findQueja(long id_queja) {
        return iQueja.findById(id_queja);
    }

    @Override
    public void updateQueja(long id_trabajador, QuejasAclaracionesModel quejasAclaracionesModel) {
        Optional<TrabajadorModel> oTrabajador = iTrabajador.findById(id_trabajador);
        quejasAclaracionesModel.setTrabajadorModel(oTrabajador.get());
        iQueja.save(quejasAclaracionesModel);
    }


    @Override
    public void eliminarQueja( long id_queja) {
        iQueja.deleteById(id_queja);
    }



}