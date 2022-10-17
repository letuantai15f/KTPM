package com.example.microserviceClient;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;





@RestController
@RequestMapping("/chuyen-bay")
public class ChuyenBayController {
	@Autowired
	private ChuyenBayRepository repo;

	@GetMapping("")
	public List<ChuyenBay> get() {
		List<ChuyenBay> lstChuyenBay = (List<ChuyenBay>) repo.findAll();
		System.out.println(lstChuyenBay);
		return lstChuyenBay;
	}
	@GetMapping("/dad")
	public List<ChuyenBay> chuyenbaydalat() {
		List<ChuyenBay> lstChuyenBay = (List<ChuyenBay>) repo.chuyenbayDAD("DAD");
		System.out.println(lstChuyenBay);
		return lstChuyenBay;
	}
	
	

	@PostMapping("")
	public ChuyenBay add(@RequestBody ChuyenBay chuyenBay) {
		repo.save(chuyenBay);
		System.out.println(chuyenBay);
		return chuyenBay;
	}
}
