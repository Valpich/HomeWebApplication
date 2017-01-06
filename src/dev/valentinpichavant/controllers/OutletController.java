package dev.valentinpichavant.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

/**
 * Created by valentinpichavant on 06/01/17.
 */
@Controller
public class OutletController {

    @Autowired
    private HttpServletRequest request;

    @RequestMapping(value = "/outlet", method = RequestMethod.GET)
    public ModelAndView showOutletPage(Principal principal) {

        if (principal == null) return new ModelAndView("user/login");
        return new ModelAndView("outlet");
    }
}
