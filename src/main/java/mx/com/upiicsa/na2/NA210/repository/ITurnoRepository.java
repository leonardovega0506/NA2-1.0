package mx.com.upiicsa.na2.NA210.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITurnoRepository extends JpaRepository<TurnosModel,Long> {
}
