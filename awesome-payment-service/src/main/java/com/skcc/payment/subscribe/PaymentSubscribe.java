package com.skcc.payment.subscribe;

import org.springframework.beans.factory.annotation.Autowired;

import com.skcc.payment.service.PaymentService;

public class PaymentSubscribe {

	private PaymentService paymentService;
	
	@Autowired
	public PaymentSubscribe(PaymentService paymentService) {
		this.paymentService = paymentService;
	}
	
}
