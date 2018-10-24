package com.skcc.payment.event.channel;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface PaymentInputChannel {
	String orderPayFailed = "orderPayFailed";

	@Input(PaymentInputChannel.orderPayFailed)
	SubscribableChannel orderPayFailed();
	
}
