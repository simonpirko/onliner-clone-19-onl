package by.tms.onlinerclone.entity;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@RequiredArgsConstructor
@Builder
public class Good {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Double price;

    @ManyToOne(cascade = CascadeType.ALL)
    private GoodCategory category;

    @OneToMany
    private List<GoodCharacters> characters;

    @ElementCollection
    private List<byte[]> photos;
}
