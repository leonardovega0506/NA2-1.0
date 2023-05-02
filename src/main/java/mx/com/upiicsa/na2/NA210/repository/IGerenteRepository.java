package mx.com.upiicsa.na2.NA210.repository;

import mx.com.upiicsa.na2.NA210.model.entity.GerenteModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IGerenteRepository extends JpaRepository<GerenteModel,Long> {
    Optional<GerenteModel> findByUsuario_IdUsuario(Long idUsuario);
}
