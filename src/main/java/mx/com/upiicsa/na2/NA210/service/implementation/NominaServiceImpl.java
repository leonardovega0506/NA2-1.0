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

import java.text.DecimalFormat;
import java.time.LocalDate;
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
    public NominaTrabajadorModel createNomina(long id_trabajador) {
        NominaTrabajadorModel nominaTrabajadorModel = new NominaTrabajadorModel();
        Optional<TrabajadorModel> oTrabajador = iTrabajador.findById(id_trabajador);
        nominaTrabajadorModel.setFechaNomina(LocalDate.now());
        nominaTrabajadorModel.setTrabajadorModel(oTrabajador.get());
        nominaTrabajadorModel.setNomina_trabajador(calcularNomina(id_trabajador, nominaTrabajadorModel));
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
        DecimalFormat df = new DecimalFormat("#.00");
        List<NominaTrabajadorModel> listNominas = findAllNominasTrabajador(id_trabajador);
        NominaTrabajadorModel nominaAnterior = new NominaTrabajadorModel();
        nominaAnterior = listNominas.get(listNominas.size()-1);
        double sueldoNormal = oTrabajador.get().getSueldo()/2;
        log.info("Sueldo Normal: {}",sueldoNormal);
        double sueldoConHoraExtras = sueldoNormal + calcularHorasExtras(id_trabajador,nominaTrabajadorModel.getFechaNomina(),nominaAnterior.getFechaNomina());
        log.info("Sueldo con Hora extra:{}",sueldoConHoraExtras);
        double sueldoRetardos = sueldoConHoraExtras - calcularDescuenbstos(id_trabajador,nominaTrabajadorModel.getFechaNomina(),nominaAnterior.getFechaNomina());
        log.info("Sueldo con Retardos: {}",sueldoRetardos);
        double sueldoISR = (sueldoRetardos) - calcularISR(id_trabajador);
        log.info("Sueldo con isr:{}",sueldoISR);
        double sueldoNETO = (sueldoISR);
        return Math.round(sueldoNETO * 100d);

    }

    private double calcularDescuenbstos(long id_trabajador,LocalDate fechaNomina,LocalDate fechaAnterior) {
        List<RetradoTrabajadorModel> retardos = sRetardo.findRetardosTrabajadorID(id_trabajador);
        double descuentoRetardo = 0;
        for (var retardo : retardos) {
            if(retardo.getFecha_retardo().isAfter(fechaAnterior) && retardo.getFecha_retardo().isBefore(fechaNomina)){
                descuentoRetardo += retardo.getDescuento_retardo();
            }
        }
        return descuentoRetardo;
    }

    private double calcularHorasExtras(long id_trabajador,LocalDate fechaNomina,LocalDate fechaanterior) {
        List<HoraExtraModel> horasExtra = sHoraExtra.findAllHoraExtraTrabajadorID(id_trabajador);
        double horasExtras = 0;
        for (var horas : horasExtra) {
            if(horas.getFecha_HoraExtra().isAfter(fechaanterior) && horas.getFecha_HoraExtra().isBefore(fechaNomina)){
                horasExtras += horas.getAumento_total();
            }

        }
        return horasExtras;
    }

    private double escogerISR(long id_trabajador) {
        Optional<TrabajadorModel> oTrabajador = iTrabajador.findById(id_trabajador);
        double sueldo = oTrabajador.get().getSueldo();
        double isrOtorgodado = 0;
        if (sueldo > 0.0 && sueldo < 644.58) {
            isrOtorgodado = 0.019;
        } else if (sueldo > 644.58 && sueldo < 5470.92) {
            isrOtorgodado = 0.064;
        } else if (sueldo > 5470.93 && sueldo < 9614.66) {
            isrOtorgodado = 0.1088;
        } else if (sueldo > 9614.67 & sueldo < 11176.62) {
            isrOtorgodado = 0.162;
        } else if (sueldo > 11176.62 && sueldo < 13381.47) {
            isrOtorgodado = 0.1792;
        } else if (sueldo > 13381.48 && sueldo < 26988.50) {
            isrOtorgodado = 0.2136;
        } else if (sueldo > 26988.51 && sueldo < 42537.58) {
            isrOtorgodado = 0.2352;
        } else if (sueldo > 42537.58 && sueldo < 81211.25) {
            isrOtorgodado = 0.3;
        } else if (sueldo > 81211.26 && sueldo < 108281.67) {
            isrOtorgodado = 0.32;
        } else if (sueldo > 108281.68 && sueldo < 324845.01) {
            isrOtorgodado = 0.34;
        } else if (sueldo > 324845.02) {
            isrOtorgodado = 0.35;
        } else {
            return 0;
        }
        return isrOtorgodado;
    }

    private double calcularISR(long id_trabajador) {
        Optional<TrabajadorModel> oTrabajador = iTrabajador.findById(id_trabajador);
        double sueldo = oTrabajador.get().getSueldo()/2;
        double isrSinCuota = 0;
        double isr = 0;
        if (sueldo > 0.0 && sueldo < 644.58) {
            isrSinCuota = (sueldo - 0.01) * escogerISR(id_trabajador);
            isr = (isrSinCuota + 0)/2;
        } else if (sueldo > 644.58 && sueldo < 5470.92) {
            isrSinCuota = (sueldo - 644.58) * escogerISR(id_trabajador);
            isr = (isrSinCuota + 12.38)/2;
        } else if (sueldo > 5470.93 && sueldo < 9614.66) {
            isrSinCuota = (sueldo - 5470.93) * escogerISR(id_trabajador);
            isr = (isrSinCuota + 321.26)/2;
        } else if (sueldo > 9614.67 & sueldo < 11176.62) {
            isrSinCuota = (sueldo - 8269.21) * escogerISR(id_trabajador);
            isr = (isrSinCuota + 772.1)/2;
        } else if (sueldo > 11176.62 && sueldo < 13381.47) {
            isrSinCuota = (sueldo - 11176.62) * escogerISR(id_trabajador);
            isr = (isrSinCuota + 1022.01)/2;
        } else if (sueldo > 13381.48 && sueldo < 26988.50) {
            isrSinCuota = (sueldo - 13381.48) * escogerISR(id_trabajador);
            isr = (isrSinCuota + 1417.12)/2;
        } else if (sueldo > 26988.51 && sueldo < 42537.58) {
            isrSinCuota = (sueldo - 26988.51) * escogerISR(id_trabajador);
            isr = (isrSinCuota + 4323.58)/2;
        } else if (sueldo > 42537.58 && sueldo < 81211.25) {
            isrSinCuota = (sueldo - 42537.58) * escogerISR(id_trabajador);
            isr = (isrSinCuota + 7980.73)/2;
        } else if (sueldo > 81211.26 && sueldo < 108281.67) {
            isrSinCuota = (sueldo - 81211.26) * escogerISR(id_trabajador);
            isr = (isrSinCuota + 19582.83)/2;
        } else if (sueldo > 108281.68 && sueldo < 324845.01) {
            isrSinCuota = (sueldo - 108281.68) * escogerISR(id_trabajador);
            isr = (isrSinCuota + 28245.36)/2;
        } else if (sueldo > 324845.02) {
            isrSinCuota = (sueldo - 324845.02) * escogerISR(id_trabajador);
            isr = (isrSinCuota + 101876.90)/2;
        } else {
            return 0;
        }

        return isr;
    }
}