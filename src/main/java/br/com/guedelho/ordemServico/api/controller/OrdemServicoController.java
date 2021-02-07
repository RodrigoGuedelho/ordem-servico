package br.com.guedelho.ordemServico.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.guedelho.ordemServico.api.exceptionhandler.Problema;
import br.com.guedelho.ordemServico.domain.models.Cliente;
import br.com.guedelho.ordemServico.domain.models.OrdemServico;
import br.com.guedelho.ordemServico.domain.repository.ClienteRepository;
import br.com.guedelho.ordemServico.domain.service.GestaoOrdemServicoService;

@RestController
@RequestMapping(value = "/api")
@CrossOrigin(origins = "*")
public class OrdemServicoController {
	
	@Autowired
	private GestaoOrdemServicoService gestaoOrdemServicoService;
	@Autowired
	private ClienteRepository ClienteRepository;
	
	@PostMapping("/ordens-servico")
	public  ResponseEntity<Object> salvar(@RequestBody OrdemServico ordemServico) {
		Cliente cliente = ClienteRepository.findById(ordemServico.getCliente().getId()).orElse(null);
		if (ordemServico.getCliente() != null &&  cliente != null) {
			ordemServico.setCliente(cliente);
			return ResponseEntity.status(201).body(gestaoOrdemServicoService.salvar(ordemServico));
		}
		
		Problema  problema = new Problema(400, "Cliente não encontrado.");
 		return ResponseEntity.status(400).body(problema);
		
	}
	
}
