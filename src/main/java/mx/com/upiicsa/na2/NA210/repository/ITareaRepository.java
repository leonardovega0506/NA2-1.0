package mx.com.upiicsa.na2.NA210.repository;

import mx.com.upiicsa.na2.NA210.model.entity.TareaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ITareaRepository extends JpaRepository<TareaModel,Long> {
    List<TareaModel> findByFecha(LocalDate fecha);
    List<TareaModel> findByTrabajadorModel_Id(Long id);
}
