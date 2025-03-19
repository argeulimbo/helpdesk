package com.lint.helpdesk.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lint.helpdesk.domain.Chamado;

public interface ChamadoRepository extends JpaRepository<Chamado, Integer> {
	
	

}
