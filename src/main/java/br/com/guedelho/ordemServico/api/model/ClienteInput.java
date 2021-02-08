package br.com.guedelho.ordemServico.api.model;

import javax.validation.constraints.NotNull;

public class ClienteInput {
	@NotNull
	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	
}
