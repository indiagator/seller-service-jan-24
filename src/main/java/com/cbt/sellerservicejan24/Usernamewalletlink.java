package com.cbt.sellerservicejan24;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "usernamewalletlinks")
public class Usernamewalletlink {
    @Id
    @Column(name = "username", nullable = false, length = 50)
    private String username;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "walletid")
    private Wallet walletid;

}