package com.course.altanto.controller;

import java.util.List;

import javax.mail.MessagingException;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.course.altanto.entity.Orders;
import com.course.altanto.entity.dto.CreateOrderDTO;
import com.course.altanto.exception.ExceptionGeneric;
import com.course.altanto.service.IOrdersService;

@RestController
@RequestMapping("/order")
public class OrderController {

	
//	MercadoPago.SDK.setAccessToken("PROD_ACCESS_TOKEN");

	
	private IOrdersService service;
	
	public OrderController(IOrdersService service) {
		this.service = service;
	}
	
	@GetMapping("/user/{id}")
	public ResponseEntity<List<Orders>> test(@PathVariable(value = "id") String id) {
		List<Orders> response = service.viewOrderByUser(id);
		 return new ResponseEntity<>(response, HttpStatus.OK);
    }
	
	@GetMapping("/{id}")
	public ResponseEntity<Orders> getOrderById(@PathVariable("id") String id) throws ExceptionGeneric {
		Orders response = service.viewOrderById(id);
		return new ResponseEntity<Orders>(response, HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Orders> deleteOrderById(@PathVariable(value = "id") String id) throws ExceptionGeneric {
		Orders response = service.deleteOrder(id);
		return new ResponseEntity<Orders>(response, HttpStatus.OK);
	}

    @GetMapping("/list")
    public ResponseEntity<List<Orders>> list(){
    	List<Orders> list = service.getAllOrders();
    	return new ResponseEntity<>(list, HttpStatus.OK);
    }
    
    @GetMapping("/paginate") 
	public ResponseEntity<Page<Orders>> paginate(@RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "id", defaultValue = "", required = false) String id,
            @RequestParam(value = "userId", defaultValue = "", required = false) String userId,
            @RequestParam(value = "dateBegin", defaultValue = "2021-01-01 23:59:00", required = false) String dateBegin,
            @RequestParam(value = "dateFinish", defaultValue = "2022-12-31 23:59:00", required = false) String dateFinish
			) {
		Page<Orders> response = service.searchOrders(pageNo, pageSize, id, userId, dateBegin, dateFinish);
		return new ResponseEntity<>(response, HttpStatus.OK);	
	}
    
    @PostMapping("/create")
    public ResponseEntity<Orders> createOrder(@RequestBody CreateOrderDTO request) throws MessagingException {
    	Orders response = service.createOrder(request);
    	return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    @PutMapping("/change-status")
    public ResponseEntity<Orders> changeStatus(@RequestParam("status") int status, @RequestParam("id") String id) {
    	Orders response = service.updateOrder(status, id);
    	return new ResponseEntity<>(response, HttpStatus.OK);    	
    }
    
    
    
}
