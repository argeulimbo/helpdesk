package com.lint.helpdesk.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.lint.helpdesk.domain.Pessoa;
import com.lint.helpdesk.domain.Tecnico;
import com.lint.helpdesk.domain.dtos.TecnicoDTO;
import com.lint.helpdesk.repositories.PessoaRepository;
import com.lint.helpdesk.repositories.TecnicoRepository;
import com.lint.helpdesk.services.exceptions.DataIntegrityViolationException;
import com.lint.helpdesk.services.exceptions.ObjectNotFoundException;

@Service
public class TecnicoService {

	@Autowired
	private TecnicoRepository repository;
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	@Autowired
	private BCryptPasswordEncoder encoder;	
	
	public Tecnico findById(Integer id) {
		Optional<Tecnico> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! ID: " + id));
	}

	public List<Tecnico> findAll() {
		return repository.findAll();
	}

	public Tecnico create(TecnicoDTO objDTO) {
		objDTO.setId(null);
		objDTO.setSenha(encoder.encode(objDTO.getSenha()));
		objDTO.setDataCriacao(LocalDate.now());
		validaPorCpfEEmail(objDTO);
		Tecnico newObj = new Tecnico(objDTO);
		return repository.save(newObj);	
	}

	private void validaPorCpfEEmail(TecnicoDTO objDTO) {
		Optional<Pessoa> obj = pessoaRepository.findByCpf(objDTO.getCpf());
		if (obj.isPresent() && obj.get().getId() != objDTO.getId()) {
			throw new DataIntegrityViolationException("CPF já cadastrado no sistema.");
		}
		obj = pessoaRepository.findByEmail(objDTO.getEmail());
		if (obj.isPresent() && obj.get().getId() != objDTO.getId()) {
			throw new DataIntegrityViolationException("E-mail já cadastrado no sistema.");
		}
	}

	public Tecnico update(Integer id, @Valid TecnicoDTO objDTO) {
		objDTO.setId(id);
		Tecnico oldObj = findById(id);
		
		// verify if password was been change
		if(!objDTO.getSenha().equals(oldObj.getSenha())) {
			objDTO.setSenha(encoder.encode(objDTO.getSenha()));
		}
		
		validaPorCpfEEmail(objDTO);
		oldObj = new Tecnico(objDTO);
		return repository.save(oldObj);		
	}

	public void delete(Integer id) {
		Tecnico obj = findById(id);	
		if (obj.getChamado().size() > 0) {
			throw new DataIntegrityViolationException("O técnico possui ordens de serviço e não pode ser deletado!");
		} else {
			repository.deleteById(id);
		}
		
	} 
	
	
}
