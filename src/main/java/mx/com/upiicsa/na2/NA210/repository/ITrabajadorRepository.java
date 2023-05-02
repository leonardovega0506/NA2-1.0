package mx.com.upiicsa.na2.NA210.repository;

import mx.com.upiicsa.na2.NA210.model.entity.TrabajadorModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ITrabajadorRepository extends JpaRepository<TrabajadorModel,Long> {
    Optional<TrabajadorModel> findByUsuario_IdUsuario(Long idUsuario);
}
