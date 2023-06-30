package by.tms.onlinerclone.entity;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Pattern;

@Getter
@Setter
public class SessionUser {

    private long id;

    private String name;
    private String username;

    private String email;

    private Address address;

    private String phoneNumber;

}

