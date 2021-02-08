package br.com.guedelho.ordemServico.api.model;

import java.math.BigDecimal;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class OrdemServicoInput {
	
	@NotBlank
	private  String descricao;
	@NotNull
	private BigDecimal preco;
	@Valid
	@NotNull
	private ClienteInput cliente;
	
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public BigDecimal getPreco() {
		return preco;
	}
	public void setPreco(BigDecimal preco) {
		this.preco = preco;
	}
	public ClienteInput getCliente() {
		return cliente;
	}
	public void setCliente(ClienteInput cliente) {
		this.cliente = cliente;
	}	
}
