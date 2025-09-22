package com.amigoscode;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigInteger;
import java.time.ZonedDateTime;

import static jakarta.persistence.GenerationType.SEQUENCE;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LibraryBook {

    @Id
    @SequenceGenerator(name = "library_id_sequence",
            sequenceName = "library_card_id_sequence",
            allocationSize = 1)

    @GeneratedValue(strategy = SEQUENCE,
            generator = "library_id_sequence")
    private Long id;

    @Column(nullable = false, columnDefinition = "TEXT")
    String title;

    @Column(nullable = false)
    private ZonedDateTime createdAt;

    @PrePersist
    void prePersist() {
        createdAt = ZonedDateTime.now();
    }

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "customer_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "library_book_customer_id_fk"), nullable = false, unique = true)
    Customer customer;

}
