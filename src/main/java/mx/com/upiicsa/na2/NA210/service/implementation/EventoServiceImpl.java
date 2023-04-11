package mx.com.upiicsa.na2.NA210.service.implementation;


import mx.com.upiicsa.na2.NA210.model.entity.EventoModel;
import mx.com.upiicsa.na2.NA210.model.entity.GerenteModel;
import mx.com.upiicsa.na2.NA210.repository.IEventoRepository;
import mx.com.upiicsa.na2.NA210.repository.IGerenteRepository;
import mx.com.upiicsa.na2.NA210.service.interfaces.IEventoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EventoServiceImpl implements IEventoService {
    @Autowired
    private IEventoRepository iEvento;

    @Autowired
    private IGerenteRepository iGerente;


    @Override
    public EventoModel createEvento(long id_gerente, EventoModel eventoModel) {
        Optional<GerenteModel> oGerente = iGerente.findById(id_gerente);
        eventoModel.setGerenteModel(oGerente.get());
        return iEvento.save(eventoModel);
    }

    @Override
    public List<EventoModel> findAllEventosGerenteID(long id_gerente) {
        List<EventoModel> eventos = iEvento.findByGerenteModel_Id(id_gerente);
        return eventos;
    }

    @Override
    public List<EventoModel> findAllEventos() {
        return iEvento.findAll();
    }

    @Override
    public Optional<EventoModel> findEvento(long id_evento) {
        Optional<EventoModel> oEvento = iEvento.findById(id_evento);
        return oEvento;
    }

    @Override
    public void updateEvento(long id_gerente, EventoModel eventoModel) {
        Optional<GerenteModel> oGerente = iGerente.findById(id_gerente);
        eventoModel.setGerenteModel(oGerente.get());
        iEvento.save(eventoModel);
    }



    @Override
    public void deleteEvento(long id_evento) {
        iEvento.deleteById(id_evento);
    }


}
