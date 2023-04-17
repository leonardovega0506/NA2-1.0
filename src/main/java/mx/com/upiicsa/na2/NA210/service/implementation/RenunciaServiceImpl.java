package mx.com.upiicsa.na2.NA210.service.implementation;


import lombok.extern.slf4j.Slf4j;
import mx.com.upiicsa.na2.NA210.model.entity.RenunciaTrabajadorModel;
import mx.com.upiicsa.na2.NA210.model.entity.TrabajadorModel;
import mx.com.upiicsa.na2.NA210.repository.INominaRepository;
import mx.com.upiicsa.na2.NA210.repository.IRenunciaRepository;
import mx.com.upiicsa.na2.NA210.repository.ITrabajadorRepository;
import mx.com.upiicsa.na2.NA210.service.interfaces.INominaService;
import mx.com.upiicsa.na2.NA210.service.interfaces.IRenunciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class RenunciaServiceImpl implements IRenunciaService {

    @Autowired
    private ITrabajadorRepository iTrabajador;

    @Autowired
    private IRenunciaRepository iRenuncia;

    @Autowired
    private INominaRepository iNomina;

    @Autowired
    private INominaService sNomina;


    @Override
    public RenunciaTrabajadorModel createRenuncia(long id_trabajador, RenunciaTrabajadorModel renunciaTrabajadorModel) {
        Optional<TrabajadorModel> oTrabajador = iTrabajador.findById(id_trabajador);
        TrabajadorModel trabajadorRenuncia = oTrabajador.get();
        renunciaTrabajadorModel.setTrabajadorModel(oTrabajador.get());
        long cuentaNominas = iNomina.countByTrabajadorModel_Id(id_trabajador);
        double mesesTrabajados = cuentaNominas/2;
        trabajadorRenuncia.setEstatus("Pendiente de Renuncia");
        renunciaTrabajadorModel.setTiempoTrabajado((int) mesesTrabajados);
        iTrabajador.save(trabajadorRenuncia);
        renunciaTrabajadorModel.setTrabajadorModel(trabajadorRenuncia);
        BigDecimal bd = new BigDecimal(obtenerFiniquito(id_trabajador,renunciaTrabajadorModel));
        bd = bd.setScale(2, RoundingMode.HALF_UP);
        renunciaTrabajadorModel.setFiniquito(bd.doubleValue());
        return iRenuncia.save(renunciaTrabajadorModel);
    }

    @Override
    public Optional<RenunciaTrabajadorModel> findRenuncia(long id_trabajador) {

        return iRenuncia.findByTrabajadorModel_Id(id_trabajador);
    }

    @Override
    public void updateRenuncia(long id_trabajador, RenunciaTrabajadorModel renunciaTrabajadorModel) {
        Optional<TrabajadorModel> oTrabajador = iTrabajador.findById(id_trabajador);
        TrabajadorModel trabajadorRenuncia = oTrabajador.get();
        renunciaTrabajadorModel.setTrabajadorModel(trabajadorRenuncia);
        iRenuncia.save(renunciaTrabajadorModel);
    }

    @Override
    public List<RenunciaTrabajadorModel> findAllRenuncias() {
        return iRenuncia.findAll();
    }

    private double obtenerFiniquito(long id_trabajador, RenunciaTrabajadorModel renunciaTrabajadorModel){
        Optional<TrabajadorModel> oTrabajador = iTrabajador.findById(id_trabajador);


        renunciaTrabajadorModel.setTrabajadorModel(oTrabajador.get());
        long cuentaNominas = iNomina.countByTrabajadorModel_Id(id_trabajador);
        double mesesTrabajados = cuentaNominas/2;

        log.info("Meses trabajados: {}",mesesTrabajados);

        double diasTrabajados = mesesTrabajados*30;
        log.info("Dias trabajados: {}",diasTrabajados);

        double sueldoDiario = oTrabajador.get().getSueldo()/diasTrabajados;
        log.info("Sueldo diario:{} ",sueldoDiario);

        double diasVacaciones = obtenerdiasVacaciones(mesesTrabajados,diasTrabajados);
        log.info("Dias vacaciones: {}",diasVacaciones);

        double aguinaldoBruto = (sueldoDiario)*(diasTrabajados/15);
        log.info("Aguinaldo: {}",aguinaldoBruto);
        double primaAntiguedad=0;
        if(mesesTrabajados>0 &&mesesTrabajados<12){
            primaAntiguedad =0;
        }
        else{
            primaAntiguedad = (sueldoDiario*12)*(mesesTrabajados/12);
            log.info("prima antiguedad: {}",primaAntiguedad);
        }

        double primaVacacional=(sueldoDiario*diasVacaciones)*.25;
        log.info("Prima vacacional: {}",primaVacacional);

        double aguinaldoNeto = (aguinaldoBruto);
        log.info("Aguinaldo: {}",aguinaldoNeto);

        double finiquitoNeto= primaVacacional+aguinaldoNeto+primaAntiguedad;
        log.info("Finiquito: {}",finiquitoNeto);

        return  finiquitoNeto;
    }
    private double escogerISR(long id_trabajador) {
        Optional<TrabajadorModel> oTrabajador = iTrabajador.findById(id_trabajador);
        double sueldo = oTrabajador.get().getSueldo();
        double isrOtorgodado = 0;
        if (sueldo > 0.01 && sueldo < 644.58) {
            isrOtorgodado = 0.0192;
        } else if (sueldo > 644.59 && sueldo < 5470.92) {
            isrOtorgodado = 0.064;
        } else if (sueldo > 5470.93 && sueldo < 9614.66) {
            isrOtorgodado = 0.1088;
        } else if (sueldo > 9614.66 & sueldo < 11176.62) {
            isrOtorgodado = 0.16;
        } else if (sueldo > 11176.62 && sueldo < 12009.94) {
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

        if (sueldo > 0 && sueldo < 644.58) {
            isrSinCuota = (sueldo - 0.01) * escogerISR(id_trabajador);
            isr = isrSinCuota + 0;
        } else if (sueldo > 644.59 && sueldo < 5470.92) {
            isrSinCuota = (sueldo - 578.53) * escogerISR(id_trabajador);
            isr = isrSinCuota + 11.11;
        } else if (sueldo > 5470.93 && sueldo < 9614.66) {
            isrSinCuota = (sueldo - 4910.19) * escogerISR(id_trabajador);
            isr = isrSinCuota + 288.33;
        } else if (sueldo > 9614.66 & sueldo < 11176.62) {
            isrSinCuota = (sueldo - 8269.21) * escogerISR(id_trabajador);
            isr = isrSinCuota + 692.96;
        } else if (sueldo > 11176.63 && sueldo < 12009.94) {
            isrSinCuota = (sueldo - 10031.08) * escogerISR(id_trabajador);
            isr = isrSinCuota + 917.26;
        } else if (sueldo > 12009.95 && sueldo < 24222.31) {
            isrSinCuota = (sueldo - 12009.95) * escogerISR(id_trabajador);
            isr = isrSinCuota + 1271.87;
        } else if (sueldo > 24222.32 && sueldo < 38177.69){
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



    private double obtenerdiasVacaciones(double mesesTrabajados,double diasTrabajados){
        double diasVacaciones=0;

        if(mesesTrabajados<12){
            diasVacaciones =365/ (diasTrabajados*12);
        }
        else if(mesesTrabajados >0 && mesesTrabajados < 24){
            diasVacaciones =365/ (diasTrabajados*12);
        }
        else if(mesesTrabajados >= 24 && mesesTrabajados < 36){
            diasVacaciones =365/ (diasTrabajados*14);
        }
        else if(mesesTrabajados >= 36 && mesesTrabajados < 48){
            diasVacaciones =365/ (diasTrabajados*18);
        }
        else if (mesesTrabajados >= 48 && mesesTrabajados < 60) {
            diasVacaciones = 365/ (diasTrabajados*20);
        }
        else if(mesesTrabajados >= 60 && mesesTrabajados< 120){
            diasVacaciones =365/ (diasTrabajados*22);
        }
        else if(mesesTrabajados >=120 && mesesTrabajados<180){
            diasVacaciones =365/ (diasTrabajados*24);
        }
        else if(mesesTrabajados >= 180 && mesesTrabajados<240){
            diasVacaciones =365/ (diasTrabajados*26);
        }
        else if(mesesTrabajados>= 140 && mesesTrabajados<300){
            diasVacaciones =365/ (diasTrabajados*28);
        }
        else if(mesesTrabajados>= 300 && mesesTrabajados<360){
            diasVacaciones =365/ (diasTrabajados*30);
        }
        else if(mesesTrabajados>= 360 && mesesTrabajados<420){
            diasVacaciones =365/ (diasTrabajados*32);
        }

        return diasVacaciones;
    }


}