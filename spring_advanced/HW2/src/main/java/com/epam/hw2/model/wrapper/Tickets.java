package com.epam.hw2.model.wrapper;

import com.epam.hw2.model.Ticket;

import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Created by Andrii_Pinchuk on 12/10/2015.
 */
@XmlRootElement(name = "tickets")
public class Tickets {
    private List<Ticket> tickets;

    public List<Ticket> getTickets() {
        return tickets;
    }

    @XmlAnyElement
    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }
}

