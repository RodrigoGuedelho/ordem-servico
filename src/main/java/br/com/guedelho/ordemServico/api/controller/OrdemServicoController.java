package br.com.guedelho.ordemServico.api.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.guedelho.ordemServico.api.exceptionhandler.Problema;
import br.com.guedelho.ordemServico.api.model.OrdemServicoInput;
import br.com.guedelho.ordemServico.api.model.OrdemServicoModel;
import br.com.guedelho.ordemServico.domain.exception.NegocioException;
import br.com.guedelho.ordemServico.domain.models.Cliente;
import br.com.guedelho.ordemServico.domain.models.OrdemServico;
import br.com.guedelho.ordemServico.domain.repository.ClienteRepository;
import br.com.guedelho.ordemServico.domain.repository.OrdemServicoRepository;
import br.com.guedelho.ordemServico.domain.service.GestaoOrdemServicoService;
import br.com.guedelho.ordemServico.uti.Utilitario;

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
	@Autowired
	private ModelMapper modelMapper;
	
	@PostMapping("/ordens-servico")
	public  ResponseEntity<Object> salvar(@Valid @RequestBody OrdemServicoInput ordemServico) {
		Cliente cliente = ClienteRepository.findById(ordemServico.getCliente().getId()).orElse(null);
		OrdemServico ordemS = toEntity(ordemServico);
		if (ordemS.getCliente() != null &&  cliente != null) {
			ordemS.setCliente(cliente);
			return ResponseEntity.status(201).body(toModel(gestaoOrdemServicoService.salvar(ordemS)));
		}
		
		Problema  problema = new Problema(400, "Cliente não encontrado.");
 		return ResponseEntity.status(400).body(problema);
		
	}
	
	@GetMapping("/ordens-servico")
	public List<OrdemServicoModel> findAll() {
		return toCollectionModel(ordemServicoRepository.findAll());
	}
	
	@GetMapping("/ordens-servico/{id}")
	public  ResponseEntity<OrdemServicoModel> findById(@PathVariable(value = "id") Long id) {
		Optional<OrdemServico>  ordemServico = ordemServicoRepository.findById(id);
		
		if (ordemServico.isPresent()) {
			return ResponseEntity.ok(toModel(ordemServico.get()));
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
	public ResponseEntity<OrdemServicoModel> editar(@Valid @PathVariable Long id, @RequestBody OrdemServicoInput ordemServicoInput) {
		
		if (ordemServicoRepository.existsById(id)) {
			OrdemServico ordemServico = toEntity(ordemServicoInput);
			ordemServico.setId(id);
			return ResponseEntity.ok(toModel(gestaoOrdemServicoService.salvar(ordemServico)));
		}
		
		return ResponseEntity.notFound().build();
	}
	@PutMapping("/ordens-servico/{id}/finalizar")
	public ResponseEntity<Object> finalizar(@PathVariable Long id) {
		try {
			gestaoOrdemServicoService.finarlizarOrdemServico(id);
			return ResponseEntity.noContent().build();
		} catch (NegocioException e) {
			return ResponseEntity.status(e.getStatus()).body(Utilitario.toModel(e));
		}
	}
	
	private OrdemServicoModel toModel(OrdemServico ordemServico) {
		return modelMapper.map(ordemServico, OrdemServicoModel.class);
	}
	
	
	private List<OrdemServicoModel> toCollectionModel(List<OrdemServico> ordensServico) {
		List<OrdemServicoModel> ordemServicoModels = new ArrayList<>();
		
		for (OrdemServico ordemServico : ordensServico) {
			ordemServicoModels.add(toModel(ordemServico));
		}
		
		return ordemServicoModels;
	}
	
	private OrdemServico toEntity(OrdemServicoInput ordemServicoInput) {
		return modelMapper.map(ordemServicoInput, OrdemServico.class);
	}
	
	
}
