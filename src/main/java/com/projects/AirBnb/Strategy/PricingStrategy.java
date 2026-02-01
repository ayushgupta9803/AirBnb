package com.projects.AirBnb.Strategy;

import com.projects.AirBnb.Entity.Inventory;

import java.math.BigDecimal;

public interface PricingStrategy {
    BigDecimal calculatePrice(Inventory inventory);
}
