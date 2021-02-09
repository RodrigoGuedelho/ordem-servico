package br.com.guedelho.ordemServico.api.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.guedelho.ordemServico.api.model.ComentarioInput;
import br.com.guedelho.ordemServico.api.model.ComentarioModel;
import br.com.guedelho.ordemServico.domain.exception.NegocioException;
import br.com.guedelho.ordemServico.domain.models.Comentario;
import br.com.guedelho.ordemServico.domain.repository.ComentarioRepository;
import br.com.guedelho.ordemServico.domain.repository.OrdemServicoRepository;
import br.com.guedelho.ordemServico.domain.service.GestaoOrdemServicoService;
import br.com.guedelho.ordemServico.uti.Utilitario;

@RestController
@RequestMapping(value = "/api/ordens-servico/{ordemServicoId}")
@CrossOrigin(origins = "*")
public class ComentarioController {
	@Autowired
	private GestaoOrdemServicoService gestaoOrdemServicoService;
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private ComentarioRepository comentarioRepository;
	@Autowired
	private OrdemServicoRepository ordemServicoRepository;

	@PostMapping("/comentario")
	public ResponseEntity<Object> salvar(@PathVariable Long ordemServicoId,
			@Valid @RequestBody ComentarioInput comentario) {
		try {
			return ResponseEntity.status(201).body(
					toModel(gestaoOrdemServicoService.salvarComentario(ordemServicoId, comentario.getDescricao())));
		} catch (NegocioException e) {
			return ResponseEntity.status(e.getStatus()).body(Utilitario.toModel(e));
		}
	}
	
	@PutMapping("/comentario/{comentarioId}")
	public ResponseEntity<Object> editar(@PathVariable Long ordemServicoId, @PathVariable Long comentarioId, @Valid @RequestBody ComentarioInput comentario) {
			try {
				return ResponseEntity.status(201).body(
						toModel(gestaoOrdemServicoService.editarComentario(ordemServicoId, comentarioId, comentario.getDescricao())));
			} catch (NegocioException e) {
				return ResponseEntity.status(e.getStatus()).body(Utilitario.toModel(e));
			}
	}
	
	
	@GetMapping("/comentario/")
	public ResponseEntity<Object> findAll(@PathVariable Long ordemServicoId) {
		if (!ordemServicoRepository.existsById(ordemServicoId))  {
			NegocioException e  = new NegocioException("Ordem de Serviço não existe",404);
			return ResponseEntity.status(e.getStatus()).body(Utilitario.toModel(e));
		}
		return  ResponseEntity.ok(toCollectionModel(comentarioRepository.findOrdemServicoId(ordemServicoId)));
	}
	
	public List<ComentarioModel> toCollectionModel(List<Comentario> comentarios) {
		List<ComentarioModel> comentariosModel = new ArrayList<>();
		
		for (Comentario comentario : comentarios) {
			comentariosModel.add(toModel(comentario));
		}
		
		return comentariosModel;
	}

	private ComentarioModel toModel(Comentario comentario) {
		return modelMapper.map(comentario, ComentarioModel.class);
	}
}
