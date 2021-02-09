package br.com.guedelho.ordemServico.domain.service;

import java.time.OffsetDateTime;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.guedelho.ordemServico.domain.exception.NegocioException;
import br.com.guedelho.ordemServico.domain.models.Comentario;
import br.com.guedelho.ordemServico.domain.models.OrdemServico;
import br.com.guedelho.ordemServico.domain.models.StatusOrdemServico;
import br.com.guedelho.ordemServico.domain.repository.ComentarioRepository;
import br.com.guedelho.ordemServico.domain.repository.OrdemServicoRepository;

@Service
public class GestaoOrdemServicoService {

	@Autowired
	private OrdemServicoRepository ordemServicoRepository;
	@Autowired
	private ComentarioRepository comentarioRepository;

	public OrdemServico salvar(OrdemServico ordemServico) {
		ordemServico.setStatus(StatusOrdemServico.ABERTO);
		ordemServico.setDataAbertura(OffsetDateTime.now());
		return ordemServicoRepository.save(ordemServico);
	}

	public void excluir(Long id) {
		ordemServicoRepository.deleteById(id);
	}

	public Comentario salvarComentario(Long ordemServicoId, String descricao) throws NegocioException {
		Optional<OrdemServico> ordemServico = ordemServicoRepository.findById(ordemServicoId);

		if (ordemServico.isPresent()) {
			Comentario comentario = new Comentario(ordemServico.get(), descricao);
			return comentarioRepository.save(comentario);
		}

		throw new NegocioException("Ordem de servico não exite.",  404);
	}
	
	public Comentario editarComentario(Long ordemServicoId, Long comentarioId, String descricao) throws NegocioException {
		Optional<OrdemServico> ordemServico = ordemServicoRepository.findById(ordemServicoId);
		
		if (!ordemServico.isPresent()) {
			throw new NegocioException("Ordem de servico não exite.",  404);
		} else if (!comentarioRepository.existsById(comentarioId)) {
			throw new NegocioException("Comentario nao existe.",  404);
		}
		Comentario comentario = new Comentario(ordemServico.get(), descricao);
		comentario.setId(comentarioId);
		return comentarioRepository.save(comentario);	
	}
	
	public void finarlizarOrdemServico(Long  ordemServicoId) throws NegocioException {
		OrdemServico ordemServico = ordemServicoRepository.findById(ordemServicoId).orElse(null);
		
		if (ordemServico == null) {
			throw new NegocioException("Ordem de servico não exite.",  404);
		}
		ordemServico.finalizar();
		ordemServicoRepository.save(ordemServico);
	}
}
