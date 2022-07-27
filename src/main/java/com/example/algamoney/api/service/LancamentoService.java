package com.example.algamoney.api.service;



import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.algamoney.api.model.Lancamento;
import com.example.algamoney.api.model.Pessoa;
import com.example.algamoney.api.repository.LancamentoRepository;
import com.example.algamoney.api.repository.PessoaRepository;
import com.example.algamoney.api.service.exception.PessoaInexistenteOuInativaException;

@Service
public class LancamentoService {
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	@Autowired
	private LancamentoRepository lancamentoRepository;

	
	public Lancamento salvar(@Valid Lancamento lancamento) throws PessoaInexistenteOuInativaException {
		
		@SuppressWarnings("deprecation")
		Pessoa pessoa = pessoaRepository.getOne(lancamento.getPessoa().getCodigo());
			if (pessoa == null || pessoa.isInativo()) {
				throw new PessoaInexistenteOuInativaException();
				
			}
		return lancamentoRepository.save(lancamento);
	}
	
	
	public Lancamento atualizar(Long codigo, Lancamento lancamento) throws PessoaInexistenteOuInativaException {
		Lancamento lancamentoSalvo = buscarLancamentoExistente(codigo);
		if (!lancamento.getPessoa().equals(lancamentoSalvo.getPessoa())) {
			validarPessoa(lancamento);
		}

		BeanUtils.copyProperties(lancamento, lancamentoSalvo, "codigo");

		return lancamentoRepository.save(lancamentoSalvo);
	}


	private void validarPessoa(Lancamento lancamento) throws PessoaInexistenteOuInativaException {
		Optional<Pessoa> pessoa = null;
		if (lancamento.getPessoa().getCodigo() != null) {
			pessoa = pessoaRepository.findById(lancamento.getPessoa().getCodigo());
		}

		if (pessoa == null || pessoa.get().isInativo()) {
			throw new PessoaInexistenteOuInativaException();
		}
	}


	private Lancamento buscarLancamentoExistente(Long codigo) {
		Lancamento lancamentoSalvo = lancamentoRepository.findById(codigo).get();
		if (lancamentoSalvo == null) {
			throw new IllegalArgumentException();
			
		}
		return lancamentoSalvo;
	}
      

}
