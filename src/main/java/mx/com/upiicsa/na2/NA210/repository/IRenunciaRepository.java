package mx.com.upiicsa.na2.NA210.repository;

import mx.com.upiicsa.na2.NA210.model.entity.RenunciaTrabajadorModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IRenunciaRepository extends JpaRepository<RenunciaTrabajadorModel,Long> {
    long countByTrabajadorModel_Id(Long id);
    Optional<RenunciaTrabajadorModel> findByTrabajadorModel_Id(Long id);
}
