package com.example.algamoney.api.resources;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.algamoney.api.event.RecursoCriadoEvent;
import com.example.algamoney.api.model.Pessoa;
import com.example.algamoney.api.repository.PessoaRepository;
import com.example.algamoney.api.service.PessoaService;

@RestController
@RequestMapping("/pessoa")
public class PessoaResources {
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@Autowired
	private PessoaService pessoaService;
	
	@GetMapping
	public List<Pessoa>listar(){
		return pessoaRepository.findAll();
	}
	
	@GetMapping("/{codigo}")
	public ResponseEntity<Pessoa> buscarPeloCodigo(@PathVariable Long codigo) {
	 Pessoa pessoa = pessoaRepository.findById(codigo).get();
		 
		 return pessoa != null ? ResponseEntity.ok(pessoa) : ResponseEntity.notFound().build() ; 
	}

	
	@PostMapping
	public ResponseEntity<Pessoa> criar(@Valid @RequestBody Pessoa pessoa, HttpServletResponse response) {
	Pessoa pessoaSalva = pessoaRepository.save(pessoa);
	
	publisher.publishEvent(new RecursoCriadoEvent(this, response, pessoaSalva.getId()));
		
	    return ResponseEntity.status(HttpStatus.CREATED).body(pessoaSalva) ; 
	}
	
	@DeleteMapping("/{/codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long codigo) {
		pessoaRepository.deleteById(codigo);
	}
	
	@PutMapping("/{codigo}")
	public ResponseEntity<Pessoa> atualizar(@PathVariable Long codigo, @Valid @RequestBody Pessoa pessoa){
		
		
		Pessoa pessoaSalva = pessoaService.atualizar(codigo, pessoa);
		
		return ResponseEntity.ok(pessoaSalva);
		
	}
	
	@PutMapping("/{codigo}/ativo")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void atualizarPropriedadeAtivo(@PathVariable Long codigo, @RequestBody Boolean ativo) {
		pessoaService.atualizarPropriedadeAtivo(codigo,ativo);
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
