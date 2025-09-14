package com.will.weather.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Set;

@Entity
@Table(
        name = "locations",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"longitude", "latitude"})})
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder(setterPrefix = "with")
public class Location {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private Set<User> user;

    @Column(name = "longitude", nullable = false)
    private BigDecimal longitude;

    @Column(name = "latitude", nullable = false)
    private BigDecimal latitude;
}
