package com.skcc.payment.publish;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.skcc.payment.event.channel.PaymentOutputChannel;

public class PaymentPublish {
	
	private PaymentOutputChannel paymentOutputChannel;
	
	@Value("${domain.payment.name}")
	private String domain;
	
	private static final Logger log = LoggerFactory.getLogger(PaymentPublish.class);

	@Autowired
	public PaymentPublish(PaymentOutputChannel paymentOutputChannel) {
		this.paymentOutputChannel = paymentOutputChannel;
	}
	
}
