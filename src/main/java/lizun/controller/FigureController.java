package lizun.controller;

import lizun.dto.FigureDto;
import lizun.dto.FigureUpdateDto;
import lizun.service.FigureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class FigureController {

    private FigureService service;

    @Autowired
    public FigureController(FigureService service) {
        this.service = service;
    }

    @RequestMapping(value = "/coords", method = RequestMethod.GET,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public   List<FigureDto> getAll() {
        return service.getAll();
    }


    @RequestMapping(value = "/delete/id/{id}", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public void delete(@PathVariable String id) {
        service.deleteFigure(Integer.parseInt(id));
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public  Integer addNewFigure(@RequestBody FigureDto figure) {
        return service.addNewFigure(figure);

    }

    /*
        Если используем @RestController, то @ResponseBody можно будет не ставить
        https://www.baeldung.com/spring-controller-vs-restcontroller
     */
    @RequestMapping(value = "/update", method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)

    public ResponseEntity<String> updateFigure(@RequestBody FigureUpdateDto figure) {

        boolean result=service.updateFigure(figure);
        if(result) return new ResponseEntity<>("Figure is updated", HttpStatus.OK);
        else
            return new ResponseEntity<>(
                    "Error of updating",
                    HttpStatus.BAD_REQUEST);

    }
}
