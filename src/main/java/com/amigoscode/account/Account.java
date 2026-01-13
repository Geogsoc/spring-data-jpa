package com.amigoscode.account;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

import static jakarta.persistence.GenerationType.SEQUENCE;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Account {

    @Id
    @SequenceGenerator(name = "account_id_sequence",
            sequenceName = "account_id_sequence",
            allocationSize = 1)

    @GeneratedValue(strategy = SEQUENCE,
            generator = "account_id_sequence")
    private Long id;


    private BigDecimal balance;
}
