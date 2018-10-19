package com.skcc.order.event.message;

import java.util.List;

import com.skcc.order.domain.OrderAccount;
import com.skcc.order.domain.OrderPayment;
import com.skcc.order.domain.OrderProduct;

//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//@ToString
public class OrderPayload {

	private long id;
	private long accountId;
	private long paymentId;
	private OrderAccount orderAccount;
	private OrderPayment orderPayment;
	private List<OrderProduct> orderProducts;
	private String paid;
	private String status;
	
	public OrderPayload(long id, long accountId, long paymentId, OrderAccount orderAccount, OrderPayment orderPayment,
			List<OrderProduct> orderProducts, String paid, String status) {
		super();
		this.id = id;
		this.accountId = accountId;
		this.paymentId = paymentId;
		this.orderAccount = orderAccount;
		this.orderPayment = orderPayment;
		this.orderProducts = orderProducts;
		this.paid = paid;
		this.status = status;
	}
	
	public OrderPayload() {}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getAccountId() {
		return accountId;
	}

	public void setAccountId(long accountId) {
		this.accountId = accountId;
	}

	public long getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(long paymentId) {
		this.paymentId = paymentId;
	}

	public OrderAccount getOrderAccount() {
		return orderAccount;
	}

	public void setOrderAccount(OrderAccount orderAccount) {
		this.orderAccount = orderAccount;
	}

	public OrderPayment getOrderPayment() {
		return orderPayment;
	}

	public void setOrderPayment(OrderPayment orderPayment) {
		this.orderPayment = orderPayment;
	}

	public List<OrderProduct> getOrderProducts() {
		return orderProducts;
	}

	public void setOrderProducts(List<OrderProduct> orderProducts) {
		this.orderProducts = orderProducts;
	}

	public String getPaid() {
		return paid;
	}

	public void setPaid(String paid) {
		this.paid = paid;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "OrderPayload [id=" + id + ", accountId=" + accountId + ", paymentId=" + paymentId + ", orderAccount="
				+ orderAccount + ", orderPayment=" + orderPayment + ", orderProducts=" + orderProducts + ", paid="
				+ paid + ", status=" + status + "]";
	}
	
	
}
