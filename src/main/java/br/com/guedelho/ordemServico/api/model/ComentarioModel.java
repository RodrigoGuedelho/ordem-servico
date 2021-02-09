package br.com.guedelho.ordemServico.api.model;

import java.time.OffsetDateTime;

public class ComentarioModel {
	private Long id;
	private String descricao;
	private OffsetDateTime dataEnvio;

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public OffsetDateTime getDataEnvio() {
		return dataEnvio;
	}

	public void setDataEnvio(OffsetDateTime dataEnvio) {
		this.dataEnvio = dataEnvio;
	}	
}
