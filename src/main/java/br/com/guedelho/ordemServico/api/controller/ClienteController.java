package br.com.guedelho.ordemServico.api.controller;

import java.util.List;
import java.util.Optional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.guedelho.ordemServico.api.exceptionhandler.Problema;
import br.com.guedelho.ordemServico.domain.exception.NegocioException;
import br.com.guedelho.ordemServico.domain.models.Cliente;
import br.com.guedelho.ordemServico.domain.repository.ClienteRepository;
import br.com.guedelho.ordemServico.domain.service.CadastroClienteService;

@RestController
@RequestMapping(value = "/api")
@CrossOrigin(origins = "*")
public class ClienteController {

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private CadastroClienteService clienteService;

	@GetMapping("/clientes")
	public List<Cliente> listar() {
		return clienteRepository.findAll();
	}

	@GetMapping("/clientes/nome/{nome}")
	public List<Cliente> listarPorNome(@PathVariable(value = "nome") Optional<String> nome) {

		return clienteRepository.findByNome(nome);
	}

	@GetMapping("/clientes/{id}")
	public ResponseEntity<Cliente> find(@PathVariable(value = "id") Long id) {

		Optional<Cliente> cliente = clienteRepository.findById(id);

		if (cliente.isPresent()) {
			return ResponseEntity.ok(cliente.get());
		}
		return ResponseEntity.notFound().build();
	}
	
	@GetMapping("/clientes/filtros/{nome}/{email}/{telefone}")
	public List<Cliente> findFiltros(@PathVariable String nome, @PathVariable String email, @PathVariable String telefone) {
		return clienteRepository.find(nome, email, telefone);
	}

	@PostMapping("/clientes")
	@ResponseStatus(value = HttpStatus.CREATED)
	public ResponseEntity<Object> salvar(@Valid @RequestBody Cliente cliente)  {
		try {
			try {	
				cliente = clienteService.Salvar(cliente);		
				return  ResponseEntity.ok(cliente);
			} catch (NegocioException e) {
				Problema problema = new Problema(e.getStatus(), e.getMensagem());
				return ResponseEntity.status(e.getStatus()).body(problema);
			}
		} catch (Exception e) {
			Problema problema = new Problema(400, e.getMessage());
			return ResponseEntity.status(400).body(problema);
		}
	}

	@PutMapping("clientes/{id}")
	public ResponseEntity<Cliente> editar(@Valid @PathVariable Long id, @RequestBody Cliente cliente) {

		if (!clienteRepository.existsById(id)) {
			return ResponseEntity.notFound().build();
		}

		cliente.setId(id);
		cliente = clienteRepository.save(cliente);
		return ResponseEntity.ok(cliente);
	}

	@DeleteMapping("clientes/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		if (!clienteRepository.existsById(id)) {
			return ResponseEntity.notFound().build();
		}

		clienteService.excluir(id);
		return ResponseEntity.noContent().build();
	}

}
