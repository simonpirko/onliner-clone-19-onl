package by.tms.onlinerclone.entity;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Pattern;

@Getter
@Setter
public class SessionUser {

    private long id;

    @Pattern(regexp = "([A-Za-z])*", message = "The firstname contains invalid characters")
    private String name;

    @Pattern(regexp = "\\w*", message = "The username contains invalid characters")
    private String username;

    @Pattern(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", message = "The email field does not contain a valid email address")
    private String email;
    @Pattern(regexp = "^[\\w\\s\\d,#-]+$", message = "The address is not valid")
    private Address address;

    @Pattern(regexp = "^\\+(?:[0-9] ?){6,14}[0-9]$", message = "The phone number is not valid")
    private String phoneNumber;

}

