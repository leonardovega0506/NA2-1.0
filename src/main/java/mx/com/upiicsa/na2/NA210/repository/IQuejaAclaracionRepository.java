package mx.com.upiicsa.na2.NA210.repository;

import mx.com.upiicsa.na2.NA210.model.entity.QuejasAclaracionesModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface IQuejaAclaracionRepository extends JpaRepository<QuejasAclaracionesModel,Long> {
    List<QuejasAclaracionesModel> findByFechaQueja(LocalDate fechaQueja);
    List<QuejasAclaracionesModel> findByTrabajadorModel_Id(Long id);
}
