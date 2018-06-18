package io.anhkhue.more.morewebapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class PageController {

    @RequestMapping(value = "/")
    public ModelAndView goHome(HttpServletResponse response) throws IOException {
        return new ModelAndView("phim-moi");
    }
}
