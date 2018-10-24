package com.skcc.payment.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.skcc.order.event.message.OrderEvent;
import com.skcc.payment.domain.Payment;
import com.skcc.payment.event.message.PaymentEvent;
import com.skcc.payment.event.message.PaymentEventType;
import com.skcc.payment.event.message.PaymentPayload;
import com.skcc.payment.publish.PaymentPublish;
import com.skcc.payment.repository.PaymentMapper;

@Service
public class PaymentService {

	private PaymentMapper paymentMapper;
	private PaymentPublish paymentPublish;
	
	@Autowired
	private PaymentService paymentService;
	
	@Value("${domain.payment.name}")
	String domain;
	
	private static final Logger log = LoggerFactory.getLogger(PaymentService.class);

	
	@Autowired
	public PaymentService(PaymentMapper paymentMapper, PaymentPublish paymentPublish) {
		this.paymentMapper = paymentMapper;
		this.paymentPublish = paymentPublish;
	}
	
	public boolean payPaymentAndCreatePublishEvent(long id) {
		boolean result = false;
		Payment resultPayment = this.findUnpaidPaymentById(id);
		resultPayment.setPaid("paid");
		try {
			this.paymentService.payPaymentAndCreatePublishPaymentPaidEvent(resultPayment);
			result = true;
		} catch(Exception e) {
			try {
				result = false;
				e.printStackTrace();
				this.paymentService.createPublishPaymentPayFailedEvent(resultPayment);
			}catch(Exception e1) {
				e1.printStackTrace();
			}
		}
		return result;
	}
	
	public boolean undoPayPaymentAndCreatePublishEvent(OrderEvent orderEvent) {
		boolean result = false;
		
		String txId = orderEvent.getTxId();
		Payment resultPayment = this.convertPaymentEventToPayment(this.findPreviousPaymentEvent(txId, orderEvent.getPayload().getPaymentId()));
		try {
			this.paymentService.undoPayPaymentAndCreatePublishPaymentPayUndoEvent(txId, resultPayment);
			result = true;
		} catch(Exception e) {
			try {
				result = false;
				e.printStackTrace();
				this.paymentService.createPublishPaymentPayUndoFailedEvent(txId, resultPayment);
			}catch(Exception e1) {
				e1.printStackTrace();
			}
		}
		return result;
	}
	
	@Transactional
	public void payPaymentAndCreatePublishPaymentPaidEvent(Payment payment) throws Exception{
		this.payPaymentValidationCheck(payment);
		this.setPaymentPaid(payment);
		this.createPublishPaymentEvent(null, payment, PaymentEventType.PaymentPaid);
	}
	
	@Transactional
	public void createPublishPaymentPayFailedEvent(Payment payment) throws Exception{
		this.createPublishPaymentEvent(null, payment, PaymentEventType.PaymentPayFailed);
	}
	
	@Transactional
	public void undoPayPaymentAndCreatePublishPaymentPayUndoEvent(String txId, Payment payment) throws Exception{
		this.undoPayPaymentValidationCheck(payment);
		this.undoPayPayment(payment);
		this.createPublishPaymentEvent(txId, payment, PaymentEventType.PaymentPayUndo);
	}
	
	@Transactional
	public void createPublishPaymentPayUndoFailedEvent(String txId, Payment payment) throws Exception{
		this.createPublishPaymentEvent(txId, payment, PaymentEventType.PaymentPayUndoFailed);
	}
	
	public void cancelPayment(Payment payment) {
		this.paymentMapper.cancelPayment(payment);
	}
	
	public void cancelPaymentValidationCheck(Payment payment) throws Exception {
		if(payment == null)
			throw new Exception();
		if("paid".equals(payment.getPaid()))
			throw new Exception();
	}

	public void setPaymentPaid(Payment payment) {
		this.paymentMapper.setPaymentPaid(payment.getPaid(), payment.getId());
	}
	
	public Payment createPayment(Payment payment) {
		this.paymentMapper.createPayment(payment);
		return payment;
	}
	
	public void createPublishPaymentEvent(String txId, Payment payment, PaymentEventType paymentEventType) {
		PaymentEvent paymentEvent = this.paymentService.convertPaymentToPaymentEvent(txId, payment, paymentEventType);
		this.createPaymentEvent(paymentEvent);
		this.publishPaymentEvent(paymentEvent);
	}
	
	public void createPaymentEvent(PaymentEvent paymentevent) {
		this.paymentMapper.createPaymentEvent(paymentevent);
	}
	
	public void publishPaymentEvent(PaymentEvent paymentEvent) {
		this.paymentPublish.send(paymentEvent);
	}
	
	public Payment findById(long id) {
		return this.paymentMapper.findById(id);
	}
	
	public void undoPayPayment(Payment payment) {
		this.paymentMapper.undoPayPayment(payment);
	}
	
	public long getPaymentEventId() {
		return this.paymentMapper.getPaymentEventId();
	}
	

	public List<Payment> findPaymentByAccountId(long accountId) {
		return this.paymentMapper.findPaymentByAccountId(accountId); 
	}
	
	public Payment convertOrderEventToPayment(OrderEvent orderEvent) {
		Payment payment = new Payment();
		
		payment.setId(orderEvent.getPayload().getPaymentId());
		payment.setAccountId(orderEvent.getPayload().getPaymentInfo().getAccountId());
		payment.setOrderId(orderEvent.getPayload().getPaymentInfo().getOrderId());
		payment.setPaymentMethod(orderEvent.getPayload().getPaymentInfo().getPaymentMethod());
		payment.setPaymentDetail1(orderEvent.getPayload().getPaymentInfo().getPaymentDetail1());
		payment.setPaymentDetail2(orderEvent.getPayload().getPaymentInfo().getPaymentDetail2());
		payment.setPaymentDetail3(orderEvent.getPayload().getPaymentInfo().getPaymentDetail3());
		payment.setPrice(orderEvent.getPayload().getPaymentInfo().getPrice());
		payment.setPaid(orderEvent.getPayload().getPaymentInfo().getPaid());
		payment.setActive(orderEvent.getPayload().getPaymentInfo().getActive());
		
		return payment;
	}
	
	public Payment convertPaymentEventToPayment(PaymentEvent paymentEvent) {
		Payment payment = new Payment();
		
		payment.setId(paymentEvent.getPayload().getId());
		payment.setAccountId(paymentEvent.getPayload().getAccountId());
		payment.setOrderId(paymentEvent.getPayload().getOrderId());
		payment.setPaymentMethod(paymentEvent.getPayload().getPaymentMethod());
		payment.setPaymentDetail1(paymentEvent.getPayload().getPaymentDetail1());
		payment.setPaymentDetail2(paymentEvent.getPayload().getPaymentDetail2());
		payment.setPaymentDetail3(paymentEvent.getPayload().getPaymentDetail3());
		payment.setPrice(paymentEvent.getPayload().getPrice());
		payment.setPaid(paymentEvent.getPayload().getPaid());
		payment.setActive(paymentEvent.getPayload().getActive());
		
		return payment;
	}
	
	public PaymentEvent convertPaymentToPaymentEvent(String txId, Payment payment, PaymentEventType paymentEventType) {
		log.info("in service txId : {}", txId);
		
		long id = payment.getId();
		
		if(id != 0)
			payment = this.findById(id);
		
		PaymentEvent paymentEvent = new PaymentEvent();
		paymentEvent.setId(this.getPaymentEventId());
		paymentEvent.setPaymentId(id);
		paymentEvent.setDomain(domain);
		paymentEvent.setEventType(paymentEventType);
		paymentEvent.setPayload(new PaymentPayload(payment.getId(), payment.getAccountId(), payment.getOrderId(), payment.getPaymentMethod(), payment.getPaymentDetail1(), payment.getPaymentDetail2(), payment.getPaymentDetail3(), payment.getPrice(), payment.getPaid(), payment.getActive()));
		paymentEvent.setTxId(txId);
		paymentEvent.setCreatedAt(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
		
		log.info("in service paymentEvent : {}", paymentEvent.toString());
		
		return paymentEvent;
	}
	
	public void createPaymentValidationCheck(Payment payment) throws Exception {
		if(payment.getPrice() == 0)
			throw new Exception();
		if(payment.getPaymentMethod().isEmpty())
			throw new Exception();
		if(payment.getAccountId() == 0)
			throw new Exception();
	}
	
	public Payment findUnpaidPaymentById(long id) {
		Payment payment = this.paymentMapper.findunPaidPaymentById(id);
		return payment;
	}
	
	public void payPaymentValidationCheck(Payment payment) throws Exception {
		if(payment.getPaid() == null || (payment.getPaid().equals("")))
			throw new Exception();
		if(!"active".equals(payment.getActive()))
			throw new Exception();
	}
	
	public void undoPayPaymentValidationCheck(Payment payment) {}
	
	public Payment findPaymentByOrderId(long orderId) {
		return this.paymentMapper.findPaymentByOrderId(orderId);
	}
	
	public PaymentEvent findPreviousPaymentEvent(String txId, long paymentId) {
		return this.paymentMapper.findPreviousPaymentEvent(txId, paymentId);
	}
	
	public List<PaymentEvent> getPaymentEvent() {
		return this.paymentMapper.getPaymentEvent();
	}
	
}
