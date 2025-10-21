package com.amigoscode.customeridcard;


import com.amigoscode.customer.Customer;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;

import static jakarta.persistence.GenerationType.SEQUENCE;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CustomerIdCard {
    @Id
    @SequenceGenerator(name = "customer_id_card_sequence",
            sequenceName = "customer_id_card_sequence",
            allocationSize = 1)

    @GeneratedValue(strategy = SEQUENCE,
            generator = "customer_id_card_sequence")
    private Long id;

    @Column(nullable = false, columnDefinition = "TEXT", unique = false)
    private String cardNumber;

    @OneToOne(cascade = CascadeType.MERGE, orphanRemoval = true)
    @JoinColumn(name = "customer_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "customer_id_card_customer_id_fk"), nullable = false, unique = true)
    private Customer customer;

    @Column(nullable = false)
    private ZonedDateTime createdAt;

    @PrePersist
    void prePersist() {
        createdAt = ZonedDateTime.now();
    }
}



