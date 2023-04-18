package ru.job4j.cars.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Data
@Entity
@Table(name = "owners")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Owner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private int id;

    @OneToOne
    @JoinColumn(name = "user_id", unique = true)
    private User user;
}