package by.tms.onlinerclone.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Good> goods;

    @ManyToOne(cascade = CascadeType.ALL)
    private User user;

    @ManyToOne
    private Address deliveryAddress;

    private String phoneNumber;
}
