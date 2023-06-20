package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.commons.OrderTO;
import com.example.demo.service.OrderService;

@RestController
@RequestMapping("order")
public class OrderController {

	@Autowired
	OrderService orderService;
	
	@GetMapping(path = "", produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<List<OrderTO>> getAllOrders(){
		List<OrderTO> orders = orderService.getAllOrders();
		return new ResponseEntity<>(orders, HttpStatus.OK);
	}
	
	@GetMapping(path = "/{id}",produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<OrderTO> getOrder(@PathVariable("id")Long id){
		OrderTO order = orderService.getOrderById(id);
		return ResponseEntity.ok(order);
	}
	
	
	
}
