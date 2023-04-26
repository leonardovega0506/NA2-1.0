package mx.com.upiicsa.na2.NA210.repository;

import mx.com.upiicsa.na2.NA210.model.entity.RetradoTrabajadorModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface IRetardoRepository extends JpaRepository<RetradoTrabajadorModel,Long> {
    List<RetradoTrabajadorModel> findByFechaRetardo(LocalDate fechaRetardo);
    List<RetradoTrabajadorModel> findByTrabajadorModel_Id(Long id);
}
