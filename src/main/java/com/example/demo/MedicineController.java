package com.example.demo;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.medicineentity.FileUtil;
import com.example.demo.medicineentity.Medicine;
import com.example.demo.medicineentity.MedicineService;

@RestController
@RequestMapping("/api/medicine")
@CrossOrigin("http://localhost:4200/")
public class MedicineController {
	
	@Autowired
	private MedicineService service;

	@PostMapping("/")
	public ResponseEntity<Medicine> addMedicine(@RequestBody Medicine m){
		Medicine medicine=service.addMedicine(m);
		if(medicine!=null) {
			return new ResponseEntity<Medicine>(medicine,HttpStatus.CREATED);
		}
		else {
			return new ResponseEntity<Medicine>(medicine,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@GetMapping("/")
	public List<Medicine> getAllMedicine(){
		return service.getAllMedicine();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Medicine> getMedicineById(@PathVariable int id){
		Medicine medicine=service.getMedicineById(id);
		
		if(medicine!=null) {
			return new ResponseEntity<Medicine>(medicine,HttpStatus.CREATED);
		}
		else {
			return new ResponseEntity<Medicine>(medicine,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping("{id}")
	public ResponseEntity<Object> updateMedicine(@PathVariable int id,@RequestBody Medicine newMedicine){
		Medicine medicine=service.updateMedicine(id, newMedicine);
		
		if(medicine!=null) {
			return new ResponseEntity<Object>(medicine,HttpStatus.OK);
		}
		else {
			return new ResponseEntity<Object>("no object found to update",HttpStatus.NOT_FOUND);
		}
	}
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteMedicine(@PathVariable int id){
		boolean result=service.deleteMedicine(id);
		
		if(result) {
			return new ResponseEntity<String>("object is deleted",HttpStatus.OK);
		}
		else {
			return new ResponseEntity<String>("no object is found to delete",HttpStatus.NOT_FOUND);
		}
	}
	
	@PostMapping(value="/upload", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<String> uploadFiles(@RequestPart Medicine med,@RequestParam("files") MultipartFile[] files) {
        try {
            createDirIfNotExist();

            List<String> fileNames = new ArrayList<>();

            // read and write the file to the local folder
            Arrays.asList(files).stream().forEach(file -> {
                byte[] bytes = new byte[0];
                try {
                    bytes = file.getBytes();
                    Path path=Paths.get(FileUtil.folderPath + file.getOriginalFilename());
                    Files.write(path, bytes);
                    fileNames.add(path.toString());
                } catch (IOException e) {
                	e.printStackTrace();
                }
            });
            med.setImage(fileNames.get(0));
            service.addMedicine(med);
            return ResponseEntity.status(HttpStatus.OK).body("Files uploaded successfully: " + fileNames);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Exception to upload files!");
        }
    }

    /**
     * Create directory to save files, if not exist
     */
    private void createDirIfNotExist() {
        //create directory to save the files
        File directory = new File(FileUtil.folderPath);
        if (! directory.exists()){
            directory.mkdir();
        }
    }
    
   
}