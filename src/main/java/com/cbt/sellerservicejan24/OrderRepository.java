package com.cbt.sellerservicejan24;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, String> {
    Order findByOrderid(String orderid);

    List<Order> findByOfferid(String offerid);
}