package com.skcc.order.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.skcc.order.domain.Order;
import com.skcc.order.service.OrderService;

@RestController
@RequestMapping("/v1")
public class OrderController {
	
	private OrderService orderService;
	
	@Autowired
	public OrderController(OrderService orderService) {
		this.orderService = orderService;
	}
	
	@GetMapping(value="/orders/{accountId}")
	public List<Order> findOrderByAccountId(@PathVariable long accountId) {
		return this.orderService.findOrderByAccountId(accountId); 
	}
	
	@PutMapping(value="/orders")
	public Order createOrder(@RequestBody Order order) {
		//화면에서 order생성 후 orderId를 payment 생성할 때 전달해야 한다.그냥 조회하는게 낫지 않을까 ? 그거나 그거나 인듯..
		return this.orderService.createOrderAndCreatePublishOrderEvent(order);
	}
	
//	@DeleteMapping(value="/orders/{id}") 
//	public boolean cancelOrder(@PathVariable long id) {
//		return this.orderService.cancelOrderAndCreatePublishOrderEvent(id);
//	}
	
}
