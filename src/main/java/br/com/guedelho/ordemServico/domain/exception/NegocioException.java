package br.com.guedelho.ordemServico.domain.exception;

public  class NegocioException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String mensagem;
	private Integer status;
	
	
	public NegocioException(String mensagem) {
		super(mensagem);
		this.mensagem = mensagem;
		this.status = 400;
	}
	public String getMensagem() {
		return mensagem;
	}
	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}

}
