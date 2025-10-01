package com.lint.helpdesk.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.lint.helpdesk.domain.Cliente;
import com.lint.helpdesk.domain.Pessoa;
import com.lint.helpdesk.domain.dtos.ClienteDTO;
import com.lint.helpdesk.repositories.ClienteRepository;
import com.lint.helpdesk.repositories.PessoaRepository;
import com.lint.helpdesk.services.exceptions.DataIntegrityViolationException;
import com.lint.helpdesk.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository repository;
	
	@Autowired
	private PessoaRepository pessoaRepository;	

	@Autowired
	private BCryptPasswordEncoder encoder;	
	
	public Cliente findById(Integer id) {
		Optional<Cliente> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! ID: " + id));
	}

	public List<Cliente> findAll() {
		return repository.findAll();
	}

	public Cliente create(ClienteDTO objDTO) {
		objDTO.setId(null);
		objDTO.setSenha(encoder.encode(objDTO.getSenha()));
		objDTO.setDataCriacao(LocalDate.now());
		validaPorCpfEEmail(objDTO);
		Cliente newObj = new Cliente(objDTO);
		return repository.save(newObj);	
	}

	private void validaPorCpfEEmail(ClienteDTO objDTO) {
		Optional<Pessoa> obj = pessoaRepository.findByCpf(objDTO.getCpf());
		if (obj.isPresent() && obj.get().getId() != objDTO.getId()) {
			throw new DataIntegrityViolationException("CPF já cadastrado no sistema.");
		}
		obj = pessoaRepository.findByEmail(objDTO.getEmail());
		if (obj.isPresent() && obj.get().getId() != objDTO.getId()) {
			throw new DataIntegrityViolationException("E-mail já cadastrado no sistema.");
		}
	}

	public Cliente update(Integer id, @Valid ClienteDTO objDTO) {
		objDTO.setId(id);
		Cliente oldObj = findById(id);
		
		// verify if password was been change
		if(!objDTO.getSenha().equals(oldObj.getSenha())) {
			objDTO.setSenha(encoder.encode(objDTO.getSenha()));
		}
		
		validaPorCpfEEmail(objDTO);
		oldObj = new Cliente(objDTO);
		return repository.save(oldObj);		
	}

	public void delete(Integer id) {
		Cliente obj = findById(id);	
		if (obj.getChamado().size() > 0) {
			throw new DataIntegrityViolationException("O técnico possui ordens de serviço e não pode ser deletado!");
		} else {
			repository.deleteById(id);
		}
		
	} 
	
	
}
