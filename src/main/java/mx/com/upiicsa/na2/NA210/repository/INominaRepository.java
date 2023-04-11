package mx.com.upiicsa.na2.NA210.repository;

import mx.com.upiicsa.na2.NA210.model.entity.NominaTrabajadorModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface INominaRepository extends JpaRepository<NominaTrabajadorModel,Long> {
    List<NominaTrabajadorModel> findByTrabajadorModel_Id(Long id);

    long countByTrabajadorModel_Id(long id_trabajador);
}
