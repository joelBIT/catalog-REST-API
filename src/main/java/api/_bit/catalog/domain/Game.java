package api._bit.catalog.domain;

import lombok.*;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

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

    private LocalDate releaseDate;
    private LocalDateTime createdAt;
}