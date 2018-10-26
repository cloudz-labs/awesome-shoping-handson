package com.skcc.payment.publish;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.skcc.payment.event.channel.PaymentOutputChannel;

public class PaymentPublish {
	
	private PaymentOutputChannel paymentOutputChannel;
	
	@Value("${domain.payment.name}")
	private String domain;
	
	@Autowired
	public PaymentPublish(PaymentOutputChannel paymentOutputChannel) {
		this.paymentOutputChannel = paymentOutputChannel;
	}
	
}
