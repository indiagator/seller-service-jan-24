package com.cbt.sellerservicejan24;

import com.cbt.sellerservicejan24.OrderRepository;
import com.cbt.sellerservicejan24.SellerOrderView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SellerOrderViewService
{
    @Autowired
    OrderRepository orderRepository;

    public SellerOrderView createSellerOrderView(String orderid)
    {
        SellerOrderView view = new SellerOrderView();
        view.setOrderid(orderid);
        view.setBuyername(orderRepository.findById(orderid).get().getBuyername());
        view.setBid_amnt(orderRepository.findById(orderid).get().getBid());

        return view;
    }
}
