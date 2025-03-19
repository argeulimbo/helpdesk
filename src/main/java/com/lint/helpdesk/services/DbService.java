package com.lint.helpdesk.services;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
	@Autowired
	private BCryptPasswordEncoder encoder;	
	
	
	public void instanciaDB() {
		
		//INSTANCIA TECNICO E ADICIONA PERFIL
		Tecnico tec1 = new Tecnico(null, "Valdir Cezar", "640.751.450-93", "valdir@mail.com", encoder.encode("123"));		
		tec1.addPerfil(Perfil.ADMIN);		
		Tecnico tec2 = new Tecnico(null, "Ailton Alves", "633.852.270-62", "ailton@sonner.com", encoder.encode("123"));		
		tec2.addPerfil(Perfil.ADMIN);
		Tecnico tec3 = new Tecnico(null, "Vinicius Resende", "837.426.550-78", "viniciuscarmo@sonner.com", encoder.encode("123"));		
		tec3.addPerfil(Perfil.ADMIN);
		Tecnico tec4 = new Tecnico(null, "Argeu Souza", "118.375.236-90", "argeu@mail.com", encoder.encode("123"));
		tec4.addPerfil(Perfil.ADMIN);
		
		//INSTANCIA CLIENTE E VINCULA TECNICO
		Cliente cli1 = new Cliente(null, "Linus Torvalds", "440.920.660-58", "torvalds@mail.com", encoder.encode("123"));
		Cliente cli2 = new Cliente(null, "Camilo Pitango", "661.166.950-78", "pitango@mail.com", encoder.encode("123"));
		Cliente cli3 = new Cliente(null, "Maxuel Xumaker", "320.934.680-18", "superpiloto@mail.com", encoder.encode("123"));
		
		//INSTANCIA CHAMADO E VINCULA TECNICO E CLIENTE
		Chamado c1 = new Chamado(null, Prioridade.ALTA, Status.ANDAMENTO, "Chamado 01", "Primeiro Chamado", tec1, cli1);
		Chamado c2 = new Chamado(null, Prioridade.MEDIA, Status.ANDAMENTO, "Chamado 02", "Segundo Chamado", tec1, cli2);
		Chamado c3 = new Chamado(null, Prioridade.BAIXA, Status.ANDAMENTO, "Chamado 03", "Terceiro Chamado", tec1, cli3);
		
		Chamado c4 = new Chamado(null, Prioridade.ALTA, Status.ANDAMENTO, "Chamado 04", "Quarto Chamado", tec2, cli1);
		Chamado c5 = new Chamado(null, Prioridade.MEDIA, Status.ANDAMENTO, "Chamado 05", "Quinto Chamado", tec2, cli2);
		Chamado c6 = new Chamado(null, Prioridade.BAIXA, Status.ANDAMENTO, "Chamado 06", "Sexto Chamado", tec2, cli3);
		
		Chamado c7 = new Chamado(null, Prioridade.ALTA, Status.ANDAMENTO, "Chamado 07", "Sétimo Chamado", tec3, cli1);
		Chamado c8 = new Chamado(null, Prioridade.MEDIA, Status.ANDAMENTO, "Chamado 08", "Oitavo Chamado", tec3, cli2);
		Chamado c9 = new Chamado(null, Prioridade.BAIXA, Status.ANDAMENTO, "Chamado 09", "Novo Chamado", tec3, cli3);
		
		
		tecnicoRepository.saveAll(Arrays.asList(tec1, tec2, tec3, tec4));
		clienteRepository.saveAll(Arrays.asList(cli1, cli2, cli3));
		chamadoRepository.saveAll(Arrays.asList(c1, c2, c3, c4, c5, c6, c7, c8, c9));
	}
	
	

	
}
