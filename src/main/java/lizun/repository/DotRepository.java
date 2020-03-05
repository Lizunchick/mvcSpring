package lizun.repository;

import lizun.model.Dot;
import org.springframework.data.repository.CrudRepository;

public interface DotRepository extends CrudRepository<Dot, Integer> {
    void deleteDotByFigureId(Integer id);
}
