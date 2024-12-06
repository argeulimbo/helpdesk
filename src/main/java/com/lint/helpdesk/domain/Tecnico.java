package com.lint.helpdesk.domain;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.OneToMany;

public class Tecnico extends Pessoa {
	private static final long serialVersionUID = 1L;
	
	@OneToMany(mappedBy = "tenico")
	private List<Chamado> chamado = new ArrayList<>();

	public Tecnico() {
		super();
	}

	public Tecnico(Integer id, String nome, String cpf, String email, String senha) {
		super(id, nome, cpf, email, senha);
	}

	public List<Chamado> getChamado() {
		return chamado;
	}

	public void setChamado(List<Chamado> chamado) {
		this.chamado = chamado;
	}

}