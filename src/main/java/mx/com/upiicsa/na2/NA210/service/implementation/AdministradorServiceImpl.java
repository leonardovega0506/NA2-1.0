package mx.com.upiicsa.na2.NA210.service.implementation;


import mx.com.upiicsa.na2.NA210.model.entity.AdministradorModel;
import mx.com.upiicsa.na2.NA210.repository.IAdminitradorRepository;
import mx.com.upiicsa.na2.NA210.response.AdministradorRespuesta;
import mx.com.upiicsa.na2.NA210.service.interfaces.IAdministradorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AdministradorServiceImpl implements IAdministradorService {

    @Autowired
    private IAdminitradorRepository iAdministrador;


    @Override
    public AdministradorModel crearAdministrador(AdministradorModel administradorModel) {
        return iAdministrador.save(administradorModel);
    }

    @Override
    public AdministradorRespuesta obtenerAdministradores(int numeroPagina, int sizePagina) {
        Pageable pageable = PageRequest.of(numeroPagina, sizePagina);
        Page<AdministradorModel> admins = iAdministrador.findAll(pageable);
        List<AdministradorModel> listaAdmins = admins.getContent();
        List<AdministradorModel> contenido = listaAdmins.stream().map(admin -> admin).collect(Collectors.toList());
        AdministradorRespuesta respuesta = new AdministradorRespuesta();
        respuesta.setContenido(contenido);
        respuesta.setNumeroPagina(admins.getNumber());
        respuesta.setSizePagina(admins.getSize());
        respuesta.setTotalElementos(admins.getTotalElements());
        respuesta.setTotalPaginas(admins.getTotalPages());
        respuesta.setUltima(admins.isLast());
        return respuesta;
    }

    @Override
    public Optional<AdministradorModel> obtenerAdministradorById(long id) {
        return iAdministrador.findById(id);
    }


    @Override
    public void actualizarAdministrador(AdministradorModel administradorModel) {
        iAdministrador.save(administradorModel);
    }


    @Override
    public void eliminiarAdministrador(long id) {
        iAdministrador.deleteById(id);
    }


}
