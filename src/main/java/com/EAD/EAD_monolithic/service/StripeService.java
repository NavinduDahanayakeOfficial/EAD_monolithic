package com.EAD.EAD_monolithic.service;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class StripeService {
    @PostConstruct
    public void init() {
//        Stripe.apiKey = System.getenv("STRIPE_SECRET_KEY");
        Stripe.apiKey="sk_test_51OAx1IL73EvdVLNG0o2MbhRzjG40dHKynnWvUoj6RdxnCAHfhlUZZQth7fZ5xlneRwLWhryjKBVSzx1DXuotUjk300TT6aLvEb";
    }

    public PaymentIntent createPaymentIntent(long amount) throws StripeException {
        return PaymentIntent.create(new HashMap<String, Object>() {{
            put("amount", Math.round(amount * 100));
            put("currency", "LKR");
            put("payment_method_types", new String[]{"card"});
//            put("metadata", Map.of("name", amount));
        }});
    }
}
