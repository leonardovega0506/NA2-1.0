package mx.com.upiicsa.na2.NA210.service.interfaces;


import mx.com.upiicsa.na2.NA210.model.entity.RenunciaTrabajadorModel;

import java.util.List;
import java.util.Optional;

public interface IRenunciaService {
    RenunciaTrabajadorModel createRenuncia(long id_trabajador, RenunciaTrabajadorModel renunciaTrabajadorModel);

    Optional<RenunciaTrabajadorModel> findRenuncia(long id_trabajador);

    void updateRenuncia(long id_trabajador, RenunciaTrabajadorModel renunciaTrabajadorModel);

    List<RenunciaTrabajadorModel> findAllRenuncias();
}
