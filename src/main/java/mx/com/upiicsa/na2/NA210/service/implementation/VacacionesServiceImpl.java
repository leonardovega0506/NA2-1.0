package mx.com.upiicsa.na2.NA210.service.implementation;

import mx.com.upiicsa.na2.NA210.model.entity.TrabajadorModel;
import mx.com.upiicsa.na2.NA210.model.entity.VacacionModel;
import mx.com.upiicsa.na2.NA210.repository.INominaRepository;
import mx.com.upiicsa.na2.NA210.repository.ITrabajadorRepository;
import mx.com.upiicsa.na2.NA210.repository.IVacacionesRepository;
import mx.com.upiicsa.na2.NA210.service.interfaces.ITrabajadorService;
import mx.com.upiicsa.na2.NA210.service.interfaces.IVacacionesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VacacionesServiceImpl implements IVacacionesService {

    @Autowired
    private ITrabajadorRepository iTrabajador;

    @Autowired
    private IVacacionesRepository iVacaciones;

    @Autowired
    private INominaRepository iNomina;



    @Override
    public VacacionModel createVacacion(long id_trabajador, VacacionModel vacacionModel) {
        Optional<TrabajadorModel> oTrabajador = iTrabajador.findById(id_trabajador);
        long cuentaNominas = iNomina.countByTrabajadorModel_Id(id_trabajador);
        double mesesTrabajados = cuentaNominas / 2;
        double diasTrabajados = mesesTrabajados * 30;
        double sueldoDiario = oTrabajador.get().getSueldo() / diasTrabajados;
        double primaVacacional = (sueldoDiario) * obtenerdiasVacaciones(mesesTrabajados,diasTrabajados) * .25;
        vacacionModel.setTrabajadorModel(oTrabajador.get());
        vacacionModel.setEstatus_vacacion("Pendiente de Aprobacion");
        vacacionModel.setPrima_vacacional(primaVacacional);
        return iVacaciones.save(vacacionModel);
    }

    @Override
    public List<VacacionModel> findAllVacacionesTrabajador(long id_trabajador) {
        return iVacaciones.findByTrabajadorModel_Id(id_trabajador);
    }

    @Override
    public Optional<VacacionModel> findVacaciones(long id_vacaciones) {
        return iVacaciones.findById(id_vacaciones);
    }

    @Override
    public void updateVacaciones(VacacionModel vacacionesModel) {
        iVacaciones.save(vacacionesModel);
    }

    @Override
    public List<VacacionModel> findAllVacaciones() {
        return iVacaciones.findAll();
    }

    @Override
    public List<VacacionModel> findVacacionesByDate(LocalDate fecha) {
        return iVacaciones.findByFechaCreacion(fecha);
    }


    @Override
    public void deleteVacaciones(long id_vacaciones) {
        iVacaciones.deleteById(id_vacaciones);
    }


    private double obtenerdiasVacaciones(double mesesTrabajados, double diasTrabajados) {
        double diasVacaciones = 0;

        if (mesesTrabajados < 12) {
            diasVacaciones = 365 / (diasTrabajados * 12);
        } else if (mesesTrabajados > 0 && mesesTrabajados < 24) {
            diasVacaciones = 365 / (diasTrabajados * 12);
        } else if (mesesTrabajados >= 24 && mesesTrabajados < 36) {
            diasVacaciones = 365 / (diasTrabajados * 14);
        } else if (mesesTrabajados >= 36 && mesesTrabajados < 48) {
            diasVacaciones = 365 / (diasTrabajados * 18);
        } else if (mesesTrabajados >= 48 && mesesTrabajados < 60) {
            diasVacaciones = 365 / (diasTrabajados * 20);
        } else if (mesesTrabajados >= 60 && mesesTrabajados < 120) {
            diasVacaciones = 365 / (diasTrabajados * 22);
        } else if (mesesTrabajados >= 120 && mesesTrabajados < 180) {
            diasVacaciones = 365 / (diasTrabajados * 24);
        } else if (mesesTrabajados >= 180 && mesesTrabajados < 240) {
            diasVacaciones = 365 / (diasTrabajados * 26);
        } else if (mesesTrabajados >= 140 && mesesTrabajados < 300) {
            diasVacaciones = 365 / (diasTrabajados * 28);
        } else if (mesesTrabajados >= 300 && mesesTrabajados < 360) {
            diasVacaciones = 365 / (diasTrabajados * 30);
        } else if (mesesTrabajados >= 360 && mesesTrabajados < 420) {
            diasVacaciones = 365 / (diasTrabajados * 32);
        }

        return diasVacaciones;
    }
}
