package com.cbt.sellerservicejan24;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "paymentwalletlinks")
public class Paymentwalletlink {
    @Id
    @Column(name = "linkid", nullable = false, length = 10)
    private String linkid;

    @Column(name = "paymenttype", length = 10)
    private String paymenttype;

    @Column(name = "paymentrefid", length = 10)
    private String paymentrefid;

    @Column(name = "payerwallet", nullable = false, length = 10)
    private String payerwallet;

    @Column(name = "payeewallet", nullable = false, length = 10)
    private String payeewallet;

    @Column(name = "escrowwallet", nullable = false, length = 10)
    private String escrowwallet;

    @Column(name = "amount")
    private Integer amount;

}