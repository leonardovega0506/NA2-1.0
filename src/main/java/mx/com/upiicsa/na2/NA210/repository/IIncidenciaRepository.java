package mx.com.upiicsa.na2.NA210.repository;


import mx.com.upiicsa.na2.NA210.model.entity.IncidenciaModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IIncidenciaRepository extends JpaRepository<IncidenciaModel,Long> {
    List<IncidenciaModel> findByTrabajadorModel_Id(Long id);

}
