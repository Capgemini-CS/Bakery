package com.capgemini.bakery.taxonomy.shop.model.entity;

import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Entity
@Table(name = "shops")

public class Shop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "shop_id")
    private Long id;

    @Column(name = "division")
    private String division;

    @Column(name = "region")
    private String region;

    @Column(name = "area")
    private String area;

}
