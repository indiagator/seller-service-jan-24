package com.cbt.sellerservicejan24;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SellerOfferOrdersService
{
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    OrderstatusRepository orderstatusRepository;
    @Autowired
    ProductofferRepository productofferRepository;
    @Autowired
    SellerOrderViewService sellerOrderViewService;

    public SellerOfferOrdersView createOrderList(String offerid)
    {
        List<Order> orders = orderRepository.findByOfferid(offerid);

        SellerOfferOrdersView view = new SellerOfferOrdersView();
        view.setOfferid(offerid);
        view.setOffername(productofferRepository.findById(offerid).get().getOffername());
        view.setCurrency(productofferRepository.findById(offerid).get().getCurrency());
        view.setAmount(productofferRepository.findById(offerid).get().getQty()*productofferRepository.findById(offerid).get().getUnitprice());

        List<SellerOrderView> orderViews = orders.stream().
                map(order -> sellerOrderViewService.createSellerOrderView(order.getOrderid())).
                collect(Collectors.toList());

        view.setOrders( orderViews );

        return  view;

    }

}
