package mx.com.upiicsa.na2.NA210.service.implementation;

import mx.com.upiicsa.na2.NA210.model.entity.GerenteModel;
import mx.com.upiicsa.na2.NA210.repository.IGerenteRepository;
import mx.com.upiicsa.na2.NA210.response.GerenteRespuesta;
import mx.com.upiicsa.na2.NA210.service.interfaces.IGerenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GerenteServiceImpl implements IGerenteService {

    @Autowired
    private IGerenteRepository iGerente;


    @Override
    public GerenteModel crearGerente(GerenteModel gerenteModel) {
        return iGerente.save(gerenteModel);
    }

    @Override
    public GerenteRespuesta obtenerGerentes(int numeroPagina, int sizePagina) {
        Pageable pageable = PageRequest.of(numeroPagina, sizePagina);
        Page<GerenteModel> gerentes = iGerente.findAll(pageable);
        List<GerenteModel> listaGerentes = gerentes.getContent();
        List<GerenteModel> contenido = listaGerentes.stream().map(gerente -> gerente).collect(Collectors.toList());
        GerenteRespuesta respuesta = new GerenteRespuesta();
        respuesta.setContenido(contenido);
        respuesta.setNumeroPagina(gerentes.getNumber());
        respuesta.setSizePagina(gerentes.getSize());
        respuesta.setTotalElementos(gerentes.getTotalElements());
        respuesta.setTotalPaginas(gerentes.getTotalPages());
        respuesta.setUltima(gerentes.isLast());
        return respuesta;
    }

    @Override
    public Optional<GerenteModel> obtenerGerenteByID(long numero_gerente) {
        return iGerente.findById(numero_gerente);
    }

    @Override
    public void actualizarGerente(GerenteModel gerenteDTO) {
        iGerente.save(gerenteDTO);
    }



    @Override
    public void eliminarGerente(long numero_gerente) {
        iGerente.deleteById(numero_gerente);
    }


}