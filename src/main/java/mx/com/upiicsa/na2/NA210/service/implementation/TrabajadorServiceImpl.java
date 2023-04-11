package mx.com.upiicsa.na2.NA210.service.implementation;

import mx.com.upiicsa.na2.NA210.model.entity.TrabajadorModel;
import mx.com.upiicsa.na2.NA210.repository.ITrabajadorRepository;
import mx.com.upiicsa.na2.NA210.response.TrabajadorRespuesta;
import mx.com.upiicsa.na2.NA210.service.interfaces.ITrabajadorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TrabajadorServiceImpl implements ITrabajadorService {

    @Autowired
    private ITrabajadorRepository iTrabajador;



    @Override
    public TrabajadorModel crearTrabajador(TrabajadorModel trabajadorModel) {
        return iTrabajador.save(trabajadorModel);
    }

    @Override
    public TrabajadorRespuesta obtenerTrabajadores(int numeroPagina, int sizePagina) {
        Pageable pageable = PageRequest.of(numeroPagina,sizePagina);
        Page<TrabajadorModel> trabajadores = iTrabajador.findAll(pageable);
        List<TrabajadorModel> listaTrabajadores = trabajadores.getContent();
        List<TrabajadorModel> contenido = listaTrabajadores.stream().map(trabajador ->trabajador).collect(Collectors.toList());
        TrabajadorRespuesta respuesta = new TrabajadorRespuesta();
        respuesta.setContenido(contenido);
        respuesta.setNumeroPagina(trabajadores.getNumber());
        respuesta.setSizePagina(trabajadores.getSize());
        respuesta.setTotalElementos(trabajadores.getTotalElements());
        respuesta.setTotalPaginas(trabajadores.getTotalPages());
        respuesta.setUltima(trabajadores.isLast());
        return respuesta;
    }

    @Override
    public Optional<TrabajadorModel> obtenerTrabajadorById(long id) {
        return iTrabajador.findById(id);
    }

    @Override
    public void actualizarTrabajador(TrabajadorModel trabajadorModel) {
        iTrabajador.save(trabajadorModel);
    }


    @Override
    public void eliminarTrabajador(long id) {
        iTrabajador.deleteById(id);
    }


}