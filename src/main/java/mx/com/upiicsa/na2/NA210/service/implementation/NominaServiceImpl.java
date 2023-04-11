package mx.com.upiicsa.na2.NA210.service.implementation;

import lombok.extern.slf4j.Slf4j;
import mx.com.upiicsa.na2.NA210.model.entity.HoraExtraModel;
import mx.com.upiicsa.na2.NA210.model.entity.NominaTrabajadorModel;
import mx.com.upiicsa.na2.NA210.model.entity.RetradoTrabajadorModel;
import mx.com.upiicsa.na2.NA210.model.entity.TrabajadorModel;
import mx.com.upiicsa.na2.NA210.repository.INominaRepository;
import mx.com.upiicsa.na2.NA210.repository.IRetardoRepository;
import mx.com.upiicsa.na2.NA210.repository.ITrabajadorRepository;
import mx.com.upiicsa.na2.NA210.service.interfaces.IHoraExtraService;
import mx.com.upiicsa.na2.NA210.service.interfaces.INominaService;
import mx.com.upiicsa.na2.NA210.service.interfaces.IRetardoTrabajadorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class NominaServiceImpl implements INominaService {

    @Autowired
    private IRetardoTrabajadorService sRetardo;

    @Autowired
    private IHoraExtraService sHoraExtra;

    @Autowired
    private INominaRepository iNomina;

    @Autowired
    private ITrabajadorRepository iTrabajador;


    @Override
    public NominaTrabajadorModel createNomina(long id_trabajador, NominaTrabajadorModel nominaTrabajadorModel) {
        Optional<TrabajadorModel> oTrabajador = iTrabajador.findById(id_trabajador);
        nominaTrabajadorModel.setTrabajadorModel(oTrabajador.get());
        nominaTrabajadorModel.setNomina_trabajador(calcularNomina(id_trabajador, nominaTrabajadorModel));
        nominaTrabajadorModel.setDescuento_retardo(calcularDescuenbstos(id_trabajador));
        nominaTrabajadorModel.setIsr(escogerISR(id_trabajador));
        return iNomina.save(nominaTrabajadorModel);
    }

    @Override
    public List<NominaTrabajadorModel> findAllNominasTrabajador(long id_trabajador) {
        return iNomina.findByTrabajadorModel_Id(id_trabajador);
    }

    @Override
    public List<NominaTrabajadorModel> findAllNominas() {
        return iNomina.findAll();
    }

    @Override
    public Optional<NominaTrabajadorModel> findNominaByID(long id_trabajador, long id_nomina) {

        Optional<TrabajadorModel> oTrabajador = iTrabajador.findById(id_trabajador);
        Optional<NominaTrabajadorModel> oNomina = iNomina.findById(id_nomina);

        if (oNomina.get().getTrabajadorModel().getId() != oTrabajador.get().getId()) {
            return null;
        }
        return oNomina;
    }

    @Override
    public void updateNomina(long id_trabajador, NominaTrabajadorModel nominaTrabajadorModel) {
        Optional<TrabajadorModel> oTrabajador = iTrabajador.findById(id_trabajador);
        nominaTrabajadorModel.setTrabajadorModel(oTrabajador.get());
        iNomina.save(nominaTrabajadorModel);
    }

    private double calcularNomina(long id_trabajador, NominaTrabajadorModel nominaTrabajadorModel) {
        Optional<TrabajadorModel> oTrabajador = iTrabajador.findById(id_trabajador);
        double sueldoNormal = oTrabajador.get().getSueldo();
        double sueldoConHoraExtras = sueldoNormal + calcularHorasExtras(id_trabajador);
        double sueldoRetardos = sueldoConHoraExtras + calcularDescuenbstos(id_trabajador);
        double sueldoISR = (sueldoRetardos) - calcularISR(id_trabajador);
        double sueldoNETO = (sueldoISR);
        return sueldoNETO;

    }

    private double calcularDescuenbstos(long id_trabajador) {
        List<RetradoTrabajadorModel> retardos = sRetardo.findRetardosTrabajadorID(id_trabajador);
        double descuentoRetardo = 0;
        for (var retardo : retardos) {
            descuentoRetardo += retardo.getDescuento_retardo();
        }
        return descuentoRetardo;
    }

    private double calcularHorasExtras(long id_trabajador) {
        List<HoraExtraModel> horasExtra = sHoraExtra.findAllHoraExtraTrabajadorID(id_trabajador);
        double horasExtras = 0;
        for (var horas : horasExtra) {
            horasExtras += horas.getAumento_total();
        }
        return horasExtras;
    }

    private double escogerISR(long id_trabajador) {
        Optional<TrabajadorModel> oTrabajador = iTrabajador.findById(id_trabajador);
        double sueldo = oTrabajador.get().getSueldo();
        double isrOtorgodado = 0;
        if (sueldo > 0 && sueldo < 578.53) {
            isrOtorgodado = 0.019;
        } else if (sueldo > 578.54 && sueldo < 4910.18) {
            isrOtorgodado = 0.064;
        } else if (sueldo > 4910.19 && sueldo < 8629.20) {
            isrOtorgodado = 0.1088;
        } else if (sueldo > 8629.21 & sueldo < 10031.07) {
            isrOtorgodado = 0.16;
        } else if (sueldo > 10031.08 && sueldo < 12009.94) {
            isrOtorgodado = 0.1792;
        } else if (sueldo > 12009.95 && sueldo < 24222.31) {
            isrOtorgodado = 0.2136;
        } else if (sueldo > 24222.32 && sueldo < 38177.69) {
            isrOtorgodado = 0.2352;
        } else if (sueldo > 38177.70 && sueldo < 72887.50) {
            isrOtorgodado = 0.3;
        } else if (sueldo > 72887.50 && sueldo < 97183.33) {
            isrOtorgodado = 0.32;
        } else if (sueldo > 97183.33 && sueldo < 291550.00) {
            isrOtorgodado = 0.34;
        } else if (sueldo > 291550.00) {
            isrOtorgodado = 0.35;
        } else {
            return 0;
        }
        return isrOtorgodado;
    }

    private double calcularISR(long id_trabajador) {
        Optional<TrabajadorModel> oTrabajador = iTrabajador.findById(id_trabajador);
        double sueldo = oTrabajador.get().getSueldo();
        double isrSinCuota = 0;
        double isr = 0;

        if (sueldo > 0 && sueldo < 578.53) {
            isrSinCuota = (sueldo - 0.01) * escogerISR(id_trabajador);
            isr = isrSinCuota + 0;
        } else if (sueldo > 578.54 && sueldo < 4910.18) {
            isrSinCuota = (sueldo - 578.53) * escogerISR(id_trabajador);
            isr = isrSinCuota + 11.11;
        } else if (sueldo > 4910.19 && sueldo < 8629.20) {
            isrSinCuota = (sueldo - 4910.19) * escogerISR(id_trabajador);
            isr = isrSinCuota + 288.33;
        } else if (sueldo > 8629.21 & sueldo < 10031.07) {
            isrSinCuota = (sueldo - 8269.21) * escogerISR(id_trabajador);
            isr = isrSinCuota + 692.96;
        } else if (sueldo > 10031.08 && sueldo < 12009.94) {
            isrSinCuota = (sueldo - 10031.08) * escogerISR(id_trabajador);
            isr = isrSinCuota + 917.26;
        } else if (sueldo > 12009.95 && sueldo < 24222.31) {
            isrSinCuota = (sueldo - 12009.95) * escogerISR(id_trabajador);
            isr = isrSinCuota + 1271.87;
        } else if (sueldo > 24222.32 && sueldo < 38177.69) {
            isrSinCuota = (sueldo - 24222.32) * escogerISR(id_trabajador);
            isr = isrSinCuota + 3880.44;
        } else if (sueldo > 38177.70 && sueldo < 72887.50) {
            isrSinCuota = (sueldo - 38177.70) * escogerISR(id_trabajador);
            isr = isrSinCuota + 7162.74;
        } else if (sueldo > 72887.50 && sueldo < 97183.33) {
            isrSinCuota = (sueldo - 72887.50) * escogerISR(id_trabajador);
            isr = isrSinCuota + 17575.69;
        } else if (sueldo > 97183.34 && sueldo < 291550.00) {
            isrSinCuota = (sueldo - 97183.34) * escogerISR(id_trabajador);
            isr = isrSinCuota + 25350.35;
        } else if (sueldo > 291550.01) {
            isrSinCuota = (sueldo - 291550.01) * escogerISR(id_trabajador);
            isr = isrSinCuota + 91435.02;
        } else {
            return 0;
        }
        return isr;
    }
}