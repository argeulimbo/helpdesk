package com.lint.helpdesk.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lint.helpdesk.domain.Pessoa;

public interface PessoaRepository extends JpaRepository<Pessoa, Integer> {
	
	

}
