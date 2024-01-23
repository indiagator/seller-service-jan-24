package com.cbt.sellerservicejan24;

import com.cbt.sellerservicejan24.SellerOrderView;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SellerOfferOrdersView
{
    String offerid;
    String offername;
    String currency;
    Integer amount;
    List<SellerOrderView> orders;
}
