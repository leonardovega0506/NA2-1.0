package mx.com.upiicsa.na2.NA210.repository;

import mx.com.upiicsa.na2.NA210.model.entity.VacacionModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IVacacionesRepository extends JpaRepository<VacacionModel,Long> {
    List<VacacionModel> findByTrabajadorModel_Id(Long id);

}
