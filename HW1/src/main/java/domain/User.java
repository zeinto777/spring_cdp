package domain;

import java.util.Date;
import java.util.List;

/**
 * Created by Andrii_Pinchuk on 2/6/2016.
 */
public class User {
    private long id;
    private String email;
    private String name;
    private Date birthday;

    public User(long id, String email, String name, Date birthday) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.birthday = birthday;
    }


    public long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public Date getBirthday() {
        return birthday;
    }
}
