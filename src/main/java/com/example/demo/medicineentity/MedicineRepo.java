package com.example.demo.medicineentity;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicineRepo extends JpaRepository<Medicine, Integer> {
	
	@Query("select m from Medicine m where m.name LIKE %?1%")
	public List<Medicine> searchList(String keyword);

}
