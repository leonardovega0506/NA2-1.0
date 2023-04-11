package mx.com.upiicsa.na2.NA210.service.interfaces;

import mx.com.upiicsa.na2.NA210.model.entity.QuejasAclaracionesModel;

import java.util.List;
import java.util.Optional;

public interface IQuejaService {
    QuejasAclaracionesModel createQueja(long id_trabajador, QuejasAclaracionesModel quejasAclaracionesModel);

    List<QuejasAclaracionesModel> findAllQuejasTrabajador(long id_trabajador);

    Optional<QuejasAclaracionesModel> findQueja(long id_queja);

    void updateQueja(long id_trabajador, QuejasAclaracionesModel quejasDTO);

    void eliminarQueja(long id_queja);

    List<QuejasAclaracionesModel> findAllQuejas();
}
