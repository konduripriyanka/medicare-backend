package com.example.demo.medicineentity;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MedicineService {
	
	@Autowired
	private MedicineRepo repo;
	
	public Medicine addMedicine(Medicine m) {
		return repo.save(m);
	}
	public List<Medicine> getAllMedicine(){
		return repo.findAll();
	}
	public Medicine getMedicineById(int id) {
		if(repo.findById(id).isPresent()) {
			return repo.findById(id).get();
		}
		else {
			return null;
		}
	}
	
	public Medicine updateMedicine(int id,Medicine newMedicine) {
		if(repo.findById(id).isPresent()) {
			Medicine oldmedicine=repo.findById(id).get();
			oldmedicine.setName(newMedicine.getName());
			oldmedicine.setDescription(newMedicine.getDescription());
			oldmedicine.setCategory(newMedicine.getCategory());
			oldmedicine.setPrice(newMedicine.getPrice());
		
			
			return repo.save(oldmedicine);
		}
		else {
			return null;
		}
	}
	public boolean deleteMedicine(int id) {
		if(repo.findById(id).isPresent()) {
			repo.deleteById(id);
			return true;
		}
		else {
			return false;
		}
	}
	
	public List<Medicine> searchList(String keyword){
		return repo.searchList(keyword);
	}

}
