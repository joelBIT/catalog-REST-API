package api._bit.catalog.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
@Table(name = "REVIEWS")
public class Review {

    @Id
    private Long id;    // Is assigned manually due to existing data being migrated and no new reviews will be added in the future. Thus, reviews already have given IDs and should not get new ones in this database.

    @ManyToOne
    private Game game;

    @Lob
    private String review;

    @Column(name = "review_date")
    private LocalDate date;

    private int rating;
    private String heading;
    private String reviewerName;
    private String reviewerId;
    private LocalDateTime createdAt;
}
