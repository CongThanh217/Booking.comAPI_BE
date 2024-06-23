package com.hotel.api.controller;


import com.hotel.api.dto.ApiMessageDto;
import com.hotel.api.dto.ErrorCode;
import com.hotel.api.form.transaction.CreatePaymentForm;
import com.hotel.api.model.Booking;
import com.hotel.api.repository.BookingRepository;
import com.hotel.api.repository.UserRepository;
import com.hotel.api.service.EmailService;
import com.hotel.api.transaction.PaymentService;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.validation.Valid;

@RestController
@RequestMapping("/v1/transaction")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Slf4j
public class TransactionController extends ABasicController{
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    APIContext apiContext;
    @Autowired
    private PaymentService paymentService;
    @Autowired
    private EmailService emailService;


    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<String> create(@Valid @RequestBody CreatePaymentForm createPaymentForm, BindingResult bindingResult){

        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();

        Booking booking = bookingRepository.findById(createPaymentForm.getBookingId()).orElse(null);
        if (booking==null)
        {
            apiMessageDto.setMessage("Not found booking");
            apiMessageDto.setCode(ErrorCode.BOOKING_ERROR_NOT_FOUND);
            return apiMessageDto;
        }

        try {
            Payment payment = paymentService.createPayment(createPaymentForm,booking);
            for (Links link : payment.getLinks()) {
                if (link.getRel().equals("approval_url")) {
                    apiMessageDto.setData(link.getHref());
                    return apiMessageDto;
                }
            }
        } catch (PayPalRESTException e) {
            throw new RuntimeException(e);
        }

        apiMessageDto.setResult(false);
        apiMessageDto.setMessage("Paypal is not available now, please contact to our customer service");
        return apiMessageDto;
    }
    @GetMapping("/deposit/cancel")
    public ApiMessageDto<String> cancelPay(@RequestParam Long bookingId) throws MessagingException {

        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        Booking booking = bookingRepository.findById(bookingId).orElse(null);
        if (booking==null)
        {
            apiMessageDto.setMessage("order not found");
            return apiMessageDto;
        }
        if (booking.getEmail()!=null)
        {
            emailService.sendOrderPaidToEmail(booking,booking.getEmail());
        }
        apiMessageDto.setMessage("cancel");
        return apiMessageDto;
    }

    @GetMapping("/deposit/success")
    public ApiMessageDto<String> successPay(@RequestParam("paymentId") String paymentId,
                                            @RequestParam("PayerID") String payerId ,@RequestParam Long bookingId) {

        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        try {
            Payment payment = paymentService.executePayment(paymentId, payerId);
            if (payment.getState().equals("approved")) {
                Booking booking = bookingRepository.findById(bookingId).orElse(null);
                if (booking==null)
                {
                    apiMessageDto.setMessage("booking not found");
                    apiMessageDto.setResult(false);
                    return apiMessageDto;
                }
                booking.setPaymentStatus(true);
                bookingRepository.save(booking);
                if (booking.getEmail()!=null)
                {
                    emailService.sendOrderPaidToEmail(booking,booking.getEmail());
                }
            }
        } catch (PayPalRESTException | MessagingException e) {
            System.out.println("lỗi");
        }

        apiMessageDto.setMessage("payment success");
        return apiMessageDto;
    }
}