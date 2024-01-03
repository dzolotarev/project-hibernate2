package com.hiberproject2.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "store", schema = "movie")
public class Store {

    @Id
    @Column(name = "store_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Byte id;

    @OneToOne
    @JoinColumn(name = "manager_staff_id")
    private Staff staff;

    @OneToOne
    @JoinColumn(name = "address_id")
    private Address address;

    @Column(name = "last_update")
    @UpdateTimestamp
    private LocalDateTime lastUpdate;
}
