package com.example.demo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.medicineentity.CartService;
import com.example.demo.medicineentity.CartsandOrders;

@RestController
@CrossOrigin(origins="http://localhost:4200/")
@RequestMapping("/api/cart")
public class CartsandController {
	
	@Autowired
	private CartService service;
	
	@PostMapping("/")
	public ResponseEntity<CartsandOrders> addCart(@RequestBody CartsandOrders cc){
		CartsandOrders co=service.addCart(cc);
		if(co!=null) {
			return new ResponseEntity<CartsandOrders>(co,HttpStatus.CREATED);
		}
		else {
			return new ResponseEntity<CartsandOrders>(co,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/")
	public List<CartsandOrders> getall(){
		return service.getallCarts();
	}

}
