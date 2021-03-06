package com.course.altanto.controller;

import java.io.IOException;
import java.util.List;

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
import org.springframework.web.multipart.MultipartFile;

import com.course.altanto.entity.Product;
import com.course.altanto.exception.ExceptionGeneric;
import com.course.altanto.exception.NotAnImageFileException;
import com.course.altanto.service.IProductService;

@RestController
@RequestMapping("/product")
public class ProductController {

	private IProductService service;

	public ProductController(IProductService service) {
		this.service = service;
	}

	@GetMapping("/view/{id}")
	public ResponseEntity<Product> test(@PathVariable(value = "id") String id) throws ExceptionGeneric {
		Product response = service.viewProductById(id);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@GetMapping("/code-size/{code}/{size}")
	public ResponseEntity<Product> getProdcutByCodeAndSize(@PathVariable(value = "code") String code, @PathVariable(value = "size") String size) throws ExceptionGeneric {
		Product response = service.getProductByCodeAndSize(code, size);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	
	
	@GetMapping("/list-top")
	public ResponseEntity<List<Product>> listTOP() {
		List<Product> response = service.findProductTopRating();
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@GetMapping("/list")
	public ResponseEntity<List<Product>> list() {
		List<Product> response = service.getAllProducts();
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@GetMapping("/list-code/{code}")
	public ResponseEntity<List<Product>> listByCode(@PathVariable("code") String code) {
		List<Product> response = service.getProductsByCodeL(code);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@PostMapping("/list-id")
	public ResponseEntity<List<Product>> listByIds(@RequestBody List<String> request) {
		List<Product> response = service.getProductsByOrder(request);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@GetMapping("/search")
	public ResponseEntity<Page<Product>> searchEC(
			@RequestParam(value = "typeClothing", defaultValue = "", required = false) String typeClothing,    
			@RequestParam(value = "clasification", defaultValue = "", required = false) String clasification,    
			@RequestParam(value = "category", defaultValue = "", required = false) String category,    
			@RequestParam(value = "keyWord", defaultValue = "", required = false) String keyWord,    
			@RequestParam(value = "minPrice", defaultValue = "0", required = false) int minPrice,
			@RequestParam(value = "maxPrice", defaultValue = "100000", required = false) int maxPrice,
			@RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
			@RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize) {
		
		Page<Product> response = service.searchEC(typeClothing, clasification, category, keyWord, minPrice, maxPrice, pageNo, pageSize);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("/paginate")
	public ResponseEntity<Page<Product>> getPageable(
			    @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
			    @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
	            @RequestParam(value = "codeProd", defaultValue = "", required = false) String codeProd,
	            @RequestParam(value = "description", defaultValue = "", required = false) String description,
	            @RequestParam(value = "name", defaultValue = "", required = false) String name,
	            @RequestParam(value = "category", defaultValue = "", required = false) String category
            ) {
		Page<Product> response = service.search(pageNo, pageSize, codeProd, description, name, category);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@GetMapping("/code/{id}") 
	public ResponseEntity<Product> getByCodeProd(@PathVariable("id") String codeProd) throws ExceptionGeneric {
		Product response = service.getPorductByCodeProd(codeProd);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@PostMapping("/add")
	public ResponseEntity<Product> addProduct(@RequestParam("codeProd") String codeProd,
			                                  @RequestParam("name") String name,
			                                  @RequestParam("description") String description,
			                                  @RequestParam("cant") int cant,
			                                  @RequestParam("size") String size,
			                                  @RequestParam("price") double price,
			                                  @RequestParam("discount") int discount,
			                                  @RequestParam("category") String category,
			                                  @RequestParam("typeGarment") String typeGarment,
			                                  @RequestParam("typeClothing") String typeClothing,
			                                  @RequestParam("images") List<MultipartFile> images) throws NotAnImageFileException, IOException, ExceptionGeneric {
		
		Product response = service.newProduct(codeProd, name, description, cant, size, price, discount, category, typeGarment, typeClothing, images);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@PutMapping("/update")
	public ResponseEntity<Product> addProduct(@RequestParam("id") String id, 
			                                  @RequestParam("name") String name,
                                              @RequestParam("description") String description,
                                              @RequestParam("cant") int cant,
                                              @RequestParam("size") String size,
                                              @RequestParam("price") double price,
                                              @RequestParam("discount") int discount
			                                  ) throws ExceptionGeneric {
		Product response = service.editProduct(id, name, description, cant, size, price, discount);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Product> deleteProduct(@PathVariable("id") String id) throws ExceptionGeneric{
		Product response = service.deleteProductById(id);
		return new ResponseEntity<>(response, HttpStatus.OK);
	} 
	
}
