package br.com.guedelho.ordemServico.uti;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.guedelho.ordemServico.api.model.NegocioExceptionModel;
import br.com.guedelho.ordemServico.domain.exception.NegocioException;

public class Utilitario {
	
	
	private static ModelMapper modelMapper =  new ModelMapper();
	
	public static NegocioExceptionModel toModel(NegocioException e) {
		return modelMapper.map(e, NegocioExceptionModel.class);
	}
}
