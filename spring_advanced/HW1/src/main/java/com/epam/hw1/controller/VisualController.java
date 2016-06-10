package com.epam.hw1.controller;

import com.epam.hw1.model.Ticket;
import com.epam.hw1.facade.BookingFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by Andrii_Pinchuk on 12/8/2015.
 */
@Controller
@RequestMapping("/ticket")
public class VisualController {
    private static final Logger LOG = LoggerFactory.getLogger(VisualController.class);

    @Autowired
    private BookingFacade bookingFacade;

    public void setBookingFacade(BookingFacade bookingFacade) {
        this.bookingFacade = bookingFacade;
    }

    @RequestMapping(value = "/get", params = {"id", "visual=true"}, method = RequestMethod.GET)
    public String getTicketById(@RequestParam("id") Long id, @RequestParam boolean visual, ModelMap model) {
        LOG.debug("getTicketById() start. ID = " + id);
        Ticket ticket = bookingFacade.getTicketById(id);
        model.put("ticket", ticket);
        LOG.debug("getTicketById() end. Result = " + ticket);
        return "visual";
    }
}
