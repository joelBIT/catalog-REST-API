package api._bit.catalog.domain;

import lombok.*;
import lombok.experimental.SuperBuilder;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
@Table(name = "GAMES")
public class Game {

    @Id
    private Long id;    // Is assigned manually due to existing data being migrated and no new games will be added in the future. Thus, games already have given IDs and should not get new ones in this database.

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
    private LocalDateTime createdAt;
}