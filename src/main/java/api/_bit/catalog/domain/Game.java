package api._bit.catalog.domain;

import lombok.*;
import lombok.experimental.SuperBuilder;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(callSuper = true)
public class Game extends BaseEntity {

    @Column(unique = true)
    private String title;

    private int players;
    private String publisher;
    private String developer;
    private String cover;
    private boolean rom;

    @Lob
    private String description;

    @Enumerated(EnumType.STRING)
    private GameCategory category;

    @OneToMany
    @JoinColumn(name = "game_id")
    private List<Review> reviews;

    private LocalDate releaseDate;
}