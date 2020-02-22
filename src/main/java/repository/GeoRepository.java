package repository;

import model.GeoObject;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GeoRepository extends CrudRepository<GeoObject, Long> {
    GeoObject findFirstByIdEquals(Integer id);
    List<GeoObject> findAllById(Integer id);
}
