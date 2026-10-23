package com.smartbarber.domain.model.barbershop;

import com.smartbarber.domain.model.subscriptionbarber.SubscriptionBarbershop;
import com.smartbarber.domain.model.suscription.Subscription;
import lombok.Builder;

import java.util.UUID;

@Builder
public record BarbershopResponse(UUID id,
                                 String name,
                                 String location,
                                 String phone,
                                 String companyName,
                                 SubscriptionBarbershop subscription) {

}
