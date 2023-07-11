package by.tms.onlinerclone.dto;

import by.tms.onlinerclone.entity.Address;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Pattern;

@Getter
@Setter
public class RegUserDto {

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

    @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}$", message = "The password short or contain invalid characters")
    private String password;

}

