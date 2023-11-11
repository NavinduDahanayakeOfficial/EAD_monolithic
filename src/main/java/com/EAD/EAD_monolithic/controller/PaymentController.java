package com.EAD.EAD_monolithic.controller;


import com.EAD.EAD_monolithic.dto.PaymentRequestDTO;
import com.EAD.EAD_monolithic.dto.PaymentResponseDTO;
import com.EAD.EAD_monolithic.service.PaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "api/v1/payment")
@CrossOrigin("*")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/pay")
    public ResponseEntity<PaymentResponseDTO> initiatePayment(@RequestBody PaymentRequestDTO paymentRequestDTO) {
        PaymentResponseDTO responseDTO = paymentService.initiatePayment(paymentRequestDTO);
        return ResponseEntity.ok(responseDTO);
    }

}
