package com.cbt.sellerservicejan24;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "productofferstatuses")
public class Productofferstatus {
    @Id
    @Column(name = "id", nullable = false, length = 10)
    private String id;

    @Column(name = "offerid", nullable = false, length = 10)
    private String offerid;

    @Column(name = "status", length = 10)
    private String status;

}