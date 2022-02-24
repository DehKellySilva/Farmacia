package com.generation.farmacia.controller;

import java.math.BigDecimal;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.generation.farmacia.model.Produto;
import com.generation.farmacia.repository.ProdutoRepository;

@RestController
@RequestMapping("/produtos")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ProdutoController {

	@Autowired
	private ProdutoRepository produtoRepository;
	
	@GetMapping
	public ResponseEntity<List<Produto>> getAll(){
		return ResponseEntity.ok(produtoRepository.findAll());
	}
	@GetMapping("{id}")
	public ResponseEntity<Produto> getById(@PathVariable Long id){
		return produtoRepository.findById(id)
				.map(resp -> ResponseEntity.ok(resp))
				.orElse(ResponseEntity.notFound().build());
	}
	
	@GetMapping("/nome/{nome}")
	public ResponseEntity<List<Produto>> getByNome(@PathVariable String nome){
		return ResponseEntity.ok(produtoRepository.findAllByDescricaoContainingIgnoreCase(nome));
	}
	
	@GetMapping("/laboratorio/{laboratorio}")
	public ResponseEntity<List<Produto>> getByLaboratorio (@PathVariable String laboratorio){
		return ResponseEntity.ok(produtoRepository.findAllByDescricaoContainingIgnoreCase(laboratorio));
	}
	
	@PostMapping
	public ResponseEntity<Produto> postProduto(@Valid @RequestBody Produto produto){
		return ResponseEntity.status(HttpStatus.CREATED).body(produtoRepository.save(produto));
	}
	
	@PutMapping
	public ResponseEntity<Produto> putProduto(@Valid @RequestBody Produto produto){
		return produtoRepository.findById(produto.getId())
				.map(resposta -> {
					return ResponseEntity.ok().body(produtoRepository.save(produto));
				})
				.orElse(ResponseEntity.notFound().build());
	}
				
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteProduto(@PathVariable Long id){
		return produtoRepository.findById(id)
				.map(resposta -> {
				produtoRepository.deleteById(id);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
				})
			.orElse(ResponseEntity.notFound().build());
	}
	
	@GetMapping("/nome/{nome}/oulaboratorio/{laboratorio}")
	public ResponseEntity<List<Produto>> getByNomeOuLaboratorio(@PathVariable String nome, @PathVariable String laboratorio){
		return ResponseEntity.ok(produtoRepository.findByNomeOrLaboratorio(nome, laboratorio));
	}

	@GetMapping("preco/{preco}/{preco1}")
	public ResponseEntity<List<Produto>> getByPreco(@PathVariable BigDecimal preco, @PathVariable BigDecimal preco1){
		return ResponseEntity.ok(produtoRepository.findByPrecoBetween(preco, preco1));
	}
	}




