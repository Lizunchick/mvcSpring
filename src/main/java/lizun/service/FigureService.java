package lizun.service;

import com.sun.xml.bind.v2.model.core.ID;
import lizun.dto.FigureDto;
import lizun.dto.FigureUpdateDto;
import lizun.model.Dot;
import lizun.model.Figure;
import lizun.model.Type;
import lizun.repository.DotRepository;
import lizun.repository.FigureRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.Id;
import javax.transaction.Transactional;
import javax.validation.constraints.Null;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FigureService {
    private FigureRepository repository;
    private DotRepository dotRepository;
    static Logger logger = LoggerFactory.getLogger(FigureService.class);

    @Autowired
    public FigureService(FigureRepository repository, DotRepository dotRepository) {
        this.repository = repository;
        this.dotRepository = dotRepository;
    }

    public List<FigureDto> getAll() {
        List<Figure> figures = repository.findAll();


        List<FigureDto> dtos = figures.stream().map(
                (model)->{
                    FigureDto figureDto=new FigureDto();
                    figureDto.setId(model.getId());
                    figureDto.setType(model.getType());
                    for (Dot dot:model.getDots()
                         ) {
                        dot.setFigure(null);
                    }
                    figureDto.setCoords(model.getDots());
                    return figureDto;
                }
        ).collect(Collectors.toList());
        logger.info("Points are gotten");

        return dtos;
    }
    public void deleteFigure(Integer id)
    {
        repository.deleteById(id);
        logger.info("Figure deleted");

    }

    public Integer addNewFigure(FigureDto figureDto)
    {
        Figure figure=new Figure();
        figure.setDots(figureDto.getCoords());
        figure.setType(figureDto.getType());
        for (Dot dot:figureDto.getCoords()
             ) {
           dot.setFigure(figure);

        }
        Integer id=repository.save(figure).getId();
        logger.info("Figure was saved, adding id "+id);
        return id;

    }
    @Transactional
    public boolean updateFigure(FigureUpdateDto figureDto){

            Figure figure = repository.findById(figureDto.getId()).orElseThrow(()->new NullPointerException());

            dotRepository.deleteDotByFigureId(figure.getId());
            for (Dot dot : figureDto.getCoords()) {
                dot.setFigure(figure);
            }
            figure.setDots(figureDto.getCoords());
            logger.info("Figure was updated");
            return repository.saveAndFlush(figure) != null;


    }

}
