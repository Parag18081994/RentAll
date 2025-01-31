package com.rental.RentAllv1.model;


import jakarta.persistence.Column;

import java.time.LocalDate;

public class PayementInformation {
    @Column (name = "cardholder_name")
    private String cardholderName;

    @Column (name = "card_number")
    private String cardNumber;

    @Column (name = "expiration_date")
    private LocalDate expirationDate;

    @Column (name = "cvv")
    private String cvv;

}
