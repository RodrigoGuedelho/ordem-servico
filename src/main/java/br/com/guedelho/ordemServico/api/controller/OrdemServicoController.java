package br.com.guedelho.ordemServico.api.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.guedelho.ordemServico.api.exceptionhandler.Problema;
import br.com.guedelho.ordemServico.domain.models.Cliente;
import br.com.guedelho.ordemServico.domain.models.OrdemServico;
import br.com.guedelho.ordemServico.domain.repository.ClienteRepository;
import br.com.guedelho.ordemServico.domain.repository.OrdemServicoRepository;
import br.com.guedelho.ordemServico.domain.service.GestaoOrdemServicoService;

@RestController
@RequestMapping(value = "/api")
@CrossOrigin(origins = "*")
public class OrdemServicoController {
	
	@Autowired
	private GestaoOrdemServicoService gestaoOrdemServicoService;
	@Autowired
	private ClienteRepository ClienteRepository;
	@Autowired
	private OrdemServicoRepository ordemServicoRepository;
	
	@PostMapping("/ordens-servico")
	public  ResponseEntity<Object> salvar(@Valid @RequestBody OrdemServico ordemServico) {
		Cliente cliente = ClienteRepository.findById(ordemServico.getCliente().getId()).orElse(null);
		if (ordemServico.getCliente() != null &&  cliente != null) {
			ordemServico.setCliente(cliente);
			return ResponseEntity.status(201).body(gestaoOrdemServicoService.salvar(ordemServico));
		}
		
		Problema  problema = new Problema(400, "Cliente não encontrado.");
 		return ResponseEntity.status(400).body(problema);
		
	}
	
	@GetMapping("/ordens-servico")
	public List<OrdemServico> findAll() {
		return ordemServicoRepository.findAll();
	}
	
	@GetMapping("/ordens-servico/{id}")
	public  ResponseEntity<OrdemServico> findById(@PathVariable(value = "id") Long id) {
		Optional<OrdemServico>  ordemServico = ordemServicoRepository.findById(id);
		
		if (ordemServico.isPresent()) {
			return ResponseEntity.ok(ordemServico.get());
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/ordens-servico/{id}")
	public ResponseEntity<Void> excluir(@PathVariable Long id) {
		
		if (ordemServicoRepository.existsById(id)) {
			ordemServicoRepository.deleteById(id);
			return ResponseEntity.noContent().build();
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@PutMapping("/ordens-servico/{id}")
	public ResponseEntity<OrdemServico> editar(@Valid @PathVariable Long id, @RequestBody OrdemServico ordemServico) {
		
		if (ordemServicoRepository.existsById(id)) {
			ordemServico.setId(id);
			return ResponseEntity.ok(gestaoOrdemServicoService.salvar(ordemServico));
		}
		
		return ResponseEntity.notFound().build();
	}
	
}
