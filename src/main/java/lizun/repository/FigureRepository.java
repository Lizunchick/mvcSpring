package lizun.repository;

import lizun.model.Figure;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface FigureRepository extends CrudRepository<Figure, Integer> {
    List<Figure> findAll();
    void deleteById(Integer id);
    Figure save(Figure figure);
    Optional<Figure> findById(Integer id);
    Figure saveAndFlush(Figure figure);


     //Figure findLastById();
}