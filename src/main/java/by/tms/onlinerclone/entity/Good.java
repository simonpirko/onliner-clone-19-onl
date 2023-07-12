package by.tms.onlinerclone.entity;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Good {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    private BigDecimal price;

    @ManyToOne(cascade = CascadeType.ALL)
    private GoodCategory category;

    @OneToMany
    private List<GoodCharacters> characters;

    @ElementCollection
    private List<byte[]> photos;
}
