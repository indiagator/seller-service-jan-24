package com.cbt.sellerservicejan24;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "productoffers")
public class Productoffer {
    @Id
    @Column(name = "id", nullable = false, length = 10)
    private String id;

    @Column(name = "hscode", nullable = false, length = 10)
    private String hscode;

    @Column(name = "qty")
    private Integer qty;

    @Column(name = "unitprice")
    private Integer unitprice;

    @Column(name = "offername", length = 50)
    private String offername;

    @Column(name = "sellername", nullable = false, length = 10)
    private String sellername;

    @Column(name = "currency", length = 10)
    private String currency;

}