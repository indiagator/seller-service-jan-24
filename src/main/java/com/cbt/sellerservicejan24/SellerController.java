package com.cbt.sellerservicejan24;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/seller")
public class SellerController
{
    @Autowired
    ProductofferRepository productofferRepository;
    @Autowired
    SellerOfferOrdersService sellerOfferOrdersService;
    @Autowired
    ProductofferstatusRepository productofferstatusRepository;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    OrderstatusRepository orderstatusRepository;
    @Autowired
    UsernamewalletlinkRepository usernamewalletlinkRepository;
    @Autowired
    PaymentRepository paymentRepository;
    @Autowired
    PaymentwalletlinkRepository paymentwalletlinkRepository;
    @Autowired
    WebClient.Builder webclientbuilder;

    Logger logger = LoggerFactory.getLogger(SellerController.class);

    @GetMapping("get/orders/{sellername}")
    public ResponseEntity<List<SellerOfferOrdersView>> getOrdersSellerwise(@PathVariable String sellername)
    {
        List<Productoffer> productofferList = productofferRepository.findBySellernameIgnoreCase(sellername);
        List<SellerOfferOrdersView> view = productofferList.stream().
                map(productoffer -> sellerOfferOrdersService.createOrderList(productoffer.getId())).
                collect(Collectors.toList());

        logger.info("returned orders for: "+sellername);
        return ResponseEntity.ok(view);
    }

    @PostMapping("accept/order/{orderid}")
    public ResponseEntity<String> acceptOrder(@PathVariable String orderid)
    {

        // REQUEST --> OFFER-SERVICE
        Order order =  orderRepository.findByOrderid(orderid);
        String offerid = order.getOfferid();
//        productofferstatusRepository.updateStatusByOfferid("PROCESSING",offerid);


//        String responseOfferService = webclientbuilder.build().post().
//                uri("http://localhost:8072/offer-service/api/v1/offer/update/offer/status/"+"PROCESSING/"+offerid).
//                retrieve().bodyToMono(String.class).block(); // SYNCHRONOUS REQUEST USING WEB FLUX

        String responseOfferService =  updateOfferRequest(offerid);



        // REQUEST --> ORDER-SERVICE
//        List<Order> orders = orderRepository.findByOfferid(offerid);
//        orders.stream().
//                forEach(tempOrder ->
//                {
//                    if(!(tempOrder.getOrderid().equals(orderid)))
//                    {
//                        orderstatusRepository.updateStatusByOrderid("REJECTED",tempOrder.getOrderid());
//                    }
//                    else {orderstatusRepository.updateStatusByOrderid("ACCEPTED",tempOrder.getOrderid());}
//                });

//        String responseOrderService = webclientbuilder.build().post().
//                uri("http://localhost:8072/order-service/api/v1/order/accept/order/"+orderid).
//                retrieve().bodyToMono(String.class).block(); // SYNCHRONOUS REQUEST USING WEB FLUX

        String responseOrderService = acceptOrderRequest(orderid);


        // REQUEST --> PAYMENT-SERVICE
//        String sellername = productofferRepository.findById(offerid).get().getSellername();
//        String buyername = orderRepository.findById(orderid).get().getBuyername();
//
//        Paymentwalletlink paymentwalletlink = new Paymentwalletlink();
//        paymentwalletlink.setLinkid(String.valueOf((int) (Math.random() * 10000)));
//        paymentwalletlink.setPaymenttype("ORDER");
//        paymentwalletlink.
//                setPayerwallet(usernamewalletlinkRepository.findById(buyername).get().getWalletid());
//        paymentwalletlink.
//                setPayeewallet(usernamewalletlinkRepository.findById(sellername).get().getWalletid());
//        paymentwalletlink.
//                setEscrowwallet(usernamewalletlinkRepository.findById("indiagator").get().getWalletid());
//        paymentwalletlink.setAmount(orderRepository.findById(orderid).get().getBid());
//
//
//        Payment payment = new Payment();
//
//        payment.setId(String.valueOf((int) (Math.random() * 10000)));
//        payment.setOrderid(orderid);
//        payment.setOfferid(offerid);
//        payment.setStatus("DUE");
//        payment.setPaymentwalletlink(paymentwalletlink.getLinkid());
//
//        paymentwalletlink.setPaymentrefid(payment.getId());
//
//
//        paymentwalletlinkRepository.save(paymentwalletlink);
//        paymentRepository.save(payment);
//
//        return ResponseEntity.ok("Order Accepted and Payment Created");

//        String responsePaymentService = webclientbuilder.build().post().
//                uri("http://localhost:8072/payment-service/api/v1/payment/create/payment/"+offerid+"/"+orderid).
//                retrieve().bodyToMono(String.class).block(); // SYNCHRONOUS REQUEST USING WEB FLUX

         String responsePaymentService = createPaymentRequest(offerid, orderid);

        logger.info("order accepted and payment created");

        return ResponseEntity.ok().body(responseOfferService+" "+responseOrderService+" "+responsePaymentService);

    }

    public String updateOfferRequest(String offerid)
    {

        String responseOfferService = webclientbuilder.build().post().
                uri("http://localhost:8072/offer-service/api/v1/offer/update/offer/status/"+"PROCESSING/"+offerid).
                retrieve().bodyToMono(String.class).block(); // SYNCHRONOUS REQUEST USING WEB FLUX

        logger.info("update offer request sent");

        return responseOfferService;
    }

    public String acceptOrderRequest(String orderid)
    {
        String responseOrderService = webclientbuilder.build().post().
                uri("http://localhost:8072/order-service/api/v1/order/accept/order/"+orderid).
                retrieve().bodyToMono(String.class).block(); // SYNCHRONOUS REQUEST USING WEB FLUX

        logger.info("accept order request sent");

        return responseOrderService;
    }

    public String createPaymentRequest(String offerid, String orderid)
    {
        String responsePaymentService = webclientbuilder.build().post().
                uri("http://localhost:8072/payment-service/api/v1/payment/create/payment/"+offerid+"/"+orderid).
                retrieve().bodyToMono(String.class).block(); // SYNCHRONOUS REQUEST USING WEB FLUX

        logger.info("create payment request sent");

        return responsePaymentService;
    }
}
