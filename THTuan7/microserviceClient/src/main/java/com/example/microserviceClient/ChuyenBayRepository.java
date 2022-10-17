package com.example.microserviceClient;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;







@Repository
public interface ChuyenBayRepository extends CrudRepository<ChuyenBay, Long>{
	@Query(value = "SELECT * FROM chuyenbay.chuyenbay where GaDen='DAD'",nativeQuery = true)
	public List<ChuyenBay> chuyenbayDAD(String str);
	
	
}