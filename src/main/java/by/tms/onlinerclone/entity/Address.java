package by.tms.onlinerclone.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Pattern;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Pattern(regexp = "^[A-Za-z\\d]+\\-?[A-Za-z\\d]*", message = "This city is incorrect")
    private String city;

    @Pattern(regexp = "^[A-Za-z\\d]+\\-?[A-Za-z\\d]*", message = "This street is incorrect")
    private String street;

    @Pattern(regexp = "^\\d+?[\\-\\d]{4,9}", message = "This zip is incorrect")
    private String zip;

}
