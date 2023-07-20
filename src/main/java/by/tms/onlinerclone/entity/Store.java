package by.tms.onlinerclone.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Store {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    private User superAdmin;

    @Column(unique = true)
    private String name;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Good> goods;

    @OneToMany
    private List<User> administrators;

    private byte[] logo;
}
