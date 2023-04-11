package mx.com.upiicsa.na2.NA210.repository;

import mx.com.upiicsa.na2.NA210.model.entity.EventoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IEventoRepository extends JpaRepository<EventoModel,Long> {
    List<EventoModel> findByGerenteModel_Id(long id);
}
