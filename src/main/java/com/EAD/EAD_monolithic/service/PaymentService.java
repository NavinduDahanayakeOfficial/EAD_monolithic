package com.EAD.EAD_monolithic.service;


import com.EAD.EAD_monolithic.dto.PaymentRequestDTO;
import com.EAD.EAD_monolithic.dto.PaymentResponseDTO;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import static java.util.Objects.isNull;

@Service
@Transactional
public class PaymentService {


    private final StripeService stripeService;

    public PaymentService(StripeService stripeService) {
        this.stripeService = stripeService;
    }

    public PaymentResponseDTO initiatePayment(PaymentRequestDTO PaymentRequestDTO) {
        PaymentResponseDTO responseDTO = new PaymentResponseDTO();
        try {

            long amount = PaymentRequestDTO.getAmount();
            if (amount == 0 || isNull(amount)) {
                responseDTO.setMessage("Total Amount is 0. There must be a problem in the transaction.");
                return responseDTO;
            }


            PaymentIntent paymentIntent = stripeService.createPaymentIntent(amount);
            responseDTO.setMessage("Payment initiated");
            responseDTO.setClientSecret(paymentIntent.getClientSecret());
        } catch (StripeException e) {
            e.printStackTrace();
            responseDTO.setMessage("Internal server error");
        }
        return responseDTO;

    }
}