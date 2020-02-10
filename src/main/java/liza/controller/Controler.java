package liza.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
class Controler {

        @RequestMapping(value = "/", method = RequestMethod.GET)
        public String allFilms() {
            /*В случае, когда тебе не нужна модель можно возвращать просто String.
              Spring будет составлять имя вью так:
              spring.mvc.view.prefix + "files" + spring.mvc.view.suffix
              Эти properties указываются в application.properties
             */
            return "films";
        }

        @RequestMapping(value = "/edit", method = RequestMethod.GET)
        public String editPage() {
            return "editPage";
        }
    }

