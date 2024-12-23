package com.lint.helpdesk.services;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lint.helpdesk.domain.Chamado;
import com.lint.helpdesk.domain.Cliente;
import com.lint.helpdesk.domain.Tecnico;
import com.lint.helpdesk.domain.enums.Perfil;
import com.lint.helpdesk.domain.enums.Prioridade;
import com.lint.helpdesk.domain.enums.Status;
import com.lint.helpdesk.repositories.ChamadoRepository;
import com.lint.helpdesk.repositories.ClienteRepository;
import com.lint.helpdesk.repositories.TecnicoRepository;

@Service
public class DbService {
	
	@Autowired
	private TecnicoRepository tecnicoRepository;
	@Autowired
	private ClienteRepository clienteRepository;
	@Autowired
	private ChamadoRepository chamadoRepository;
	
	public void instanciaDB() {
		Tecnico tec1 = new Tecnico(null, "Valdir Cezar", "19435063020", "valdir@mail.com", "123");		
		tec1.addPerfil(Perfil.ADMIN);
		
		Cliente cli1 = new Cliente(null, "Linus Torvalds", "16332615060", "torvalds@mail.com", "123");
		
		Chamado c1 = new Chamado(null, Prioridade.MEDIA, Status.ANDAMENTO, "Chamado 01", "Primeiro Chamado", tec1, cli1);
		
		tecnicoRepository.saveAll(Arrays.asList(tec1));
		clienteRepository.saveAll(Arrays.asList(cli1));
		chamadoRepository.saveAll(Arrays.asList(c1));
	}

	
}
