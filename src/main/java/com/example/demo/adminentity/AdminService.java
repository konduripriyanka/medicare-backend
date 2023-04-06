package com.example.demo.adminentity;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService {
	
	@Autowired
	private AdminRepo repo;
	
	public Admin addAdmin(Admin a) {
		return repo.save(a);
	}
	
	public List<Admin> getAllAdmin(){
		return repo.findAll();
	}
	
	public Admin getAdminById(int id) {
		if(repo.findById(id).isPresent()) {
			return repo.findById(id).get();
		}
		else {
			return null;
		}
	}
	
	public Admin updateAdmin(int id,Admin newAdmin) {
		if(repo.findById(id).isPresent()) {
			Admin oldadmin=repo.findById(id).get();
			oldadmin.setName(newAdmin.getName());
			oldadmin.setEmail(newAdmin.getEmail());
			oldadmin.setCountry(newAdmin.getCountry());
			oldadmin.setLogin(newAdmin.getLogin());
			
			return repo.save(oldadmin);
		}
		else {
			return null;
		}
	}
	
	public boolean deleteAdmin(int id) {
		if(repo.findById(id).isPresent()) {
			repo.deleteById(id);
			return true;
		}
		else {
			return false;
		}
	}

}
