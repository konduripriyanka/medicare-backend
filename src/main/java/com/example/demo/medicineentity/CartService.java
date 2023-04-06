package com.example.demo.medicineentity;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartService {
	
	@Autowired
	private CartRepo repo;
	
	public CartsandOrders addCart(CartsandOrders cc) {
		return repo.save(cc);
	}
	
	public List<CartsandOrders> getallCarts(){
		return repo.findAll();
	}

}
