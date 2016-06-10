package com.epam.hw1.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class BaseController {
    private static final String VIEW_INDEX = "index";
    private static final Logger LOG = LoggerFactory.getLogger(BaseController.class);

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String welcome(ModelMap model) {
        model.addAttribute("message", "Welcome");
        LOG.debug("[welcome]  : ");
        return VIEW_INDEX;
    }
}