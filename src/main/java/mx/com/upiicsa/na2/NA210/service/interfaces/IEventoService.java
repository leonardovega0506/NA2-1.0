package mx.com.upiicsa.na2.NA210.service.interfaces;


import mx.com.upiicsa.na2.NA210.model.entity.EventoModel;

import java.util.List;
import java.util.Optional;

public interface IEventoService {
    EventoModel createEvento(long id_gerente, EventoModel eventoModel);

    List<EventoModel> findAllEventosGerenteID(long id_gerente);

    Optional<EventoModel> findEvento(long id_gerente, long id_evento);

    void updateEvento(long id_gerente,EventoModel eventoModel);

    List<EventoModel> findAllEventos();

    void deleteEvento(long id_evento);
}
