package mx.com.upiicsa.na2.NA210.repository;

import mx.com.upiicsa.na2.NA210.model.entity.HoraExtraModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IHoraExtraRepository extends JpaRepository<HoraExtraModel,Long> {
    List<HoraExtraModel> findByTrabajadorModel_Id(Long id);
}
