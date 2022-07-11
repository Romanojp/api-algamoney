package com.example.algamoney.api.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.algamoney.api.model.Lancamento;
import com.example.algamoney.api.repository.LancamentoRepository;

@RestController("/lancamentos")
public class LancamentosResources {
	
	@Autowired
	private LancamentoRepository lancamentoRepository;
	
	@GetMapping
	public List<Lancamento> listar(){
		return lancamentoRepository.findAll();
	}
	
	@GetMapping("/{codigo}")
	public ResponseEntity<Lancamento> buscarPeloCodigo(Long codigo){
		Lancamento lancamento = lancamentoRepository.findById(codigo).get();
		 
		 return lancamento != null ? ResponseEntity.ok(lancamento) : ResponseEntity.notFound().build() ; 
	}


}
