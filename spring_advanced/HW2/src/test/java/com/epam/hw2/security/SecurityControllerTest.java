package com.epam.hw2.security;

import com.epam.hw2.controller.TicketController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.ui.ModelMap;

import java.util.Date;

/**
 * Created by Andrii_Pinchuk on 6/19/2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/spring-mvc.xml")
public class SecurityControllerTest {
    @Autowired
    TicketController ticketController;

    private Date date = new Date("Nov 18, 2015");

    @Test(expected = AuthenticationCredentialsNotFoundException.class)
    public void testForbiddenEvents() {
        ticketController.getBookedTickets(35l, "Test_4186", date, 45, 45, 45, new ModelMap());
    }

    @Test(expected = AccessDeniedException.class)
    public void testWrongUserEvents() {
        Authentication auth = new UsernamePasswordAuthenticationToken("alex", "123456");
        SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(auth);

        ticketController.getBookedTickets(35l, "Test_4186", date, 45, 45, 45, new ModelMap());
        SecurityContextHolder.clearContext();
    }

    @Test
    public void testMyEvents() {
        Authentication auth = new UsernamePasswordAuthenticationToken("admin", "123456");
        SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(auth);

        ticketController.getBookedTickets(344493l, "Test_4186", date, 45, 45, 45, new ModelMap());
        SecurityContextHolder.clearContext();
    }
}
