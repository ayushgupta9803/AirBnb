package com.projects.AirBnb.Strategy;

import com.projects.AirBnb.Entity.Inventory;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;

public class BasePricingStrategy implements PricingStrategy{

    @Override
    public BigDecimal calculatePrice(Inventory inventory) {
        return inventory.getRoom().getBasePrice();
    }
}
