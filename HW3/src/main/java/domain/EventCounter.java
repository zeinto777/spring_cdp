package domain;

/**
 * Created by Andrii_Pinchuk on 2/23/2016.
 */
public class EventCounter {
    private long id;
    private long countGetEventByName;
    private long countGetTicketPrice;
    private long countBookedTicket;
    public EventCounter() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCountGetEventByName() {
        return countGetEventByName;
    }

    public void setCountGetEventByName(long countGetEventByName) {
        this.countGetEventByName = countGetEventByName;
    }

    public long getCountGetTicketPrice() {
        return countGetTicketPrice;
    }

    public void setCountGetTicketPrice(long countGetTicketPrice) {
        this.countGetTicketPrice = countGetTicketPrice;
    }

    public long getCountBookedTicket() {
        return countBookedTicket;
    }

    public void setCountBookedTicket(long countBookedTicket) {
        this.countBookedTicket = countBookedTicket;
    }
}
