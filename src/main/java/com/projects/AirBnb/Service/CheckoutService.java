package com.projects.AirBnb.Service;

import com.projects.AirBnb.Entity.Booking;

public interface CheckoutService {

    String getCheckoutSession(Booking booking , String successUrl , String failureUrl);
}
