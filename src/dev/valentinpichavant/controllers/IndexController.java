package dev.valentinpichavant.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

/**
 * Created by valentinpichavant on 11/1/16.
 */
@Controller
public class IndexController {

    @Autowired
    private HttpServletRequest request;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView showIndexPage(Principal principal) {

        if (principal == null) return new ModelAndView("user/login");
        return new ModelAndView("index");
    }
}
