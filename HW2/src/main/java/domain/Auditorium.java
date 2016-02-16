package domain;

import java.util.List;

/**
 * Created by Andrii_Pinchuk on 2/6/2016.
 */
public class Auditorium {
    private long id;
    private String name;
    private Long number_of_seats;
    private List<Long> vip_seats;

    public Auditorium(long id, String name, Long number_of_seats, List<Long> vip_seats) {
        this.id = id;
        this.name = name;
        this.number_of_seats = number_of_seats;
        this.vip_seats = vip_seats;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Long getNumber_of_seats() {
        return number_of_seats;
    }

    public List<Long> getVip_seats() {
        return vip_seats;
    }

    @Override
    public String toString() {
        return "Auditorium{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", number_of_seats=" + number_of_seats +
                ", vip_seats=" + vip_seats +
                '}';
    }
}
