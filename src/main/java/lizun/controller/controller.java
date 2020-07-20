package lizun.controller;


//ctrl-alt-O почистить все импорты в IDEA
import com.google.gson.*;
import com.sun.xml.bind.v2.model.core.ID;
import lizun.dto.FigureDto;
import lizun.dto.FigureUpdateDto;
import lizun.model.Dot;
import lizun.model.Figure;
import lizun.model.Type;
import lizun.service.FigureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;

/**
 * Разделить на 2 контроллера:
 * REST контроллер Фигур и обычный контроллер для главной страницы HTML
 */
@Controller
public class controller {

    private FigureService service;
    //Не используется
    private Id id;

    @Autowired
    public controller(FigureService service) {
        this.service = service;
    }

    //@GetMapping("/")
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index() {
        return "index";
    }

    @RequestMapping(value = "/coords", method = RequestMethod.GET,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public     @ResponseBody List<FigureDto> getAll() {
        return service.getAll();
    }

    //@PostMapping("/delete/id/{id}")
    @RequestMapping(value = "/delete/id/{id}", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public void delete(@PathVariable String id) {
        service.deleteFigure(Integer.parseInt(id));
    }
    @ResponseBody
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
    @ResponseBody
    public String updateFigure(@RequestBody FigureUpdateDto figure) {
        /**
         * Лучше сделать через ResponseEntity
         * https://www.baeldung.com/spring-response-entity
         * Если все хорошо, ставим статус 200, если возникла ошибка, то какой нибудь из 4xx
         * https://developer.mozilla.org/ru/docs/Web/HTTP/Status
         * На клиенте ответы от сервера с кодом 4xx сваливаются в errorCallback автоматически
         */
       boolean result=service.updateFigure(figure);
            System.out.println(result);
            if(result) return "true";
            else
            return "false";

    }

}
