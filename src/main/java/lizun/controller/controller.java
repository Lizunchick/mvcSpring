package lizun.controller;

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

@Controller
public class controller {

    private FigureService service;
    private Id id;

    @Autowired
    public controller(FigureService service) {
        this.service = service;
    }

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

    @RequestMapping(value = "/update", method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public void updateFigure(@RequestBody FigureUpdateDto figure) {

       boolean result=service.updateFigure(figure);
            System.out.println(result);

    }

}
