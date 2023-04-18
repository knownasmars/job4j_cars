package ru.job4j.cars.model;

import lombok.*;

import javax.persistence.*;

@Data
@Entity
@Table(name = "engines")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class Engine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private int id;
    @NonNull
    private String name;
}