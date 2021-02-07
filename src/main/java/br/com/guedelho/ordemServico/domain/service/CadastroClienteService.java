package br.com.guedelho.ordemServico.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.guedelho.ordemServico.domain.exception.NegocioException;
import br.com.guedelho.ordemServico.domain.models.Cliente;
import br.com.guedelho.ordemServico.domain.repository.ClienteRepository;

@Service
public class CadastroClienteService {
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	public Cliente Salvar(Cliente cliente) throws NegocioException   {
		
		Cliente clienteExiste = clienteRepository.findByEmail(cliente.getEmail());
		if (clienteExiste != null && !clienteExiste.equals(cliente)) {
			throw new NegocioException("Já existe um cliente cadastrado com esse email.");
		}
		
		return clienteRepository.save(cliente);
	}
	
	public void excluir(Long id) {
		clienteRepository.deleteById(id);
	}
}
