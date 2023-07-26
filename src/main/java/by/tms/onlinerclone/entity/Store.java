package by.tms.onlinerclone.entity;

import lombok.*;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

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

    @OneToOne
    private User superAdmin;

    @Column(unique = true)
    private String name;

    @OneToMany
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Good> goods;

    @OneToMany
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<User> administrators;

    private byte[] logo;
}
