package com.example.demo.service.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.commons.OrderDetailTO;
import com.example.demo.commons.OrderTO;
import com.example.demo.entity.OrderDO;
import com.example.demo.entity.OrderDetailDO;
import com.example.demo.persistance.OrderDAO;
import com.example.demo.persistance.OrderDetailDAO;
import com.example.demo.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderDAO orderDAO;
	@Autowired
	private OrderDetailDAO orderDetailDAO;
	@Override
	public List<OrderTO> getAllOrders() {
		List<OrderTO> response =new ArrayList<>();
		List<OrderDO> orderDO = orderDAO.findAll();
		for (OrderDO orderDO2 : orderDO) {
			response.add(convert(orderDO2));
		}
		
		return response;
	}

	@Override
	public OrderTO getOrderById(Long id) {
		OrderTO response = null;
		OrderDO responseDo = orderDAO.findById(id).get();
		response = convert(responseDo);
		return response;
	}
	
	

	private OrderTO convert(OrderDO responseDo) {
		OrderTO response;
		response = castingDOtoTO(responseDo);
		List<OrderDetailDO> details = orderDetailDAO.findDetailsByOrderId(responseDo.getId());
		final List<OrderDetailTO> list = new ArrayList<>();
		details.stream().forEach(orderDetailDO ->{
			list.add(castDetailDOtoTO(orderDetailDO));} );

		response.setDetails(list);
		return response;
	}

	@Override
	public Long createOrder(OrderTO order) {
		
		OrderDO response= castOrderTOtoDO(order);
		response.setId(null);
		orderDAO.save(response);
		orderDAO.flush();		
		return response.getId();
	}

	@Override
	public void editOrder(OrderTO order) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteOrder(Long id) {
		// TODO Auto-generated method stub
		
	}

	private OrderTO castingDOtoTO(OrderDO order) {
		
		OrderTO response = new OrderTO(order.getId(),order.getClientId(),
				order.getTs(),order.getTotal(), new ArrayList<>());
		return response;
	}
	
	private OrderDetailTO castDetailDOtoTO(OrderDetailDO e) {
		OrderDetailTO response = new OrderDetailTO(e.getId(), e.getOrder().getId(), 
				e.getSku(),e.getDescription(),e.getQuantity(),e.getPrice());
		return response;
	}
	
	private OrderDO castOrderTOtoDO(OrderTO order) {
		OrderDO response = new  OrderDO();
		response.setId(order.getId());
		response.setClientId(order.getClientId());
		response.setTs(order.getTimestamp());
		response.setTotal(order.getTotal());
		response.setDetails(orderDetailDAO.findDetailsByOrderId(order.getId()));		
		
		
		return response;
	}
	
	

}
