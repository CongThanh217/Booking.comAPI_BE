package com.hotel.api.transaction;


import com.hotel.api.form.transaction.CreatePaymentForm;
import com.hotel.api.model.Booking;
import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

@Service
public class PaymentService {

    @Autowired
    APIContext apiContext;

    public Payment executePayment(String paymentId, String payerId) throws PayPalRESTException {
        Payment payment = new Payment();
        payment.setId(paymentId);

        PaymentExecution paymentExecute = new PaymentExecution();
        paymentExecute.setPayerId(payerId);
        return payment.execute(apiContext, paymentExecute);
    }
    private BigDecimal convertToUSD(Double amount){
//        double convertCurrency = amount / 24230;
//        DecimalFormat decimalFormat = new DecimalFormat("#.##");
//        return decimalFormat.format(convertCurrency);
        BigDecimal amountBigDecimal = BigDecimal.valueOf(amount);
        BigDecimal exchangeRate = new BigDecimal("24230");
        return amountBigDecimal.divide(exchangeRate, 2, RoundingMode.HALF_EVEN);
    }
    public Payment createPayment(CreatePaymentForm createPaymentForm, Booking booking)throws PayPalRESTException
    {
        ItemList itemList = new ItemList();
        List<Item> items = new ArrayList<>();

        Item item = new Item();
        item.setName("Nạp tiền ");
        item.setCurrency("USD");
        item.setPrice(convertToUSD(Double.valueOf(booking.getPrice())).toString());
        item.setQuantity("1");
        items.add(item);
        itemList.setItems(items);

        Amount amount = new Amount();
        amount.setCurrency("USD");
        amount.setTotal(convertToUSD(Double.valueOf(booking.getPrice())).toString());

        Transaction transaction = new Transaction();
        transaction.setDescription("Nạp tiền ");
        transaction.setAmount(amount);
        transaction.setItemList(itemList);
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(transaction);

        Payer payer = new Payer();
        payer.setPaymentMethod("paypal");

        Payment payment = new Payment();
        payment.setIntent("sale");
        payment.setPayer(payer);
        payment.setTransactions(transactions);


        RedirectUrls redirectUrls = new RedirectUrls();
        redirectUrls.setReturnUrl(createPaymentForm.getUrlSuccess()+"?bookingId=" + booking.getId());
        redirectUrls.setCancelUrl(createPaymentForm.getUrlCancel()+"?bookingId=" + booking.getId());
        payment.setRedirectUrls(redirectUrls);
        apiContext.setMaskRequestId(true);
        return payment.create(apiContext);
    }
}
