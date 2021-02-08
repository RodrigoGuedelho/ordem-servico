package br.com.guedelho.ordemServico.domain.service;

import java.time.OffsetDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.guedelho.ordemServico.domain.models.OrdemServico;
import br.com.guedelho.ordemServico.domain.models.StatusOrdemServico;
import br.com.guedelho.ordemServico.domain.repository.OrdemServicoRepository;

@Service
public class GestaoOrdemServicoService {

	@Autowired
	private OrdemServicoRepository ordemServicoRepository;

	public OrdemServico salvar(OrdemServico ordemServico) {

		if (ordemServico.getId() == null) {
			ordemServico.setStatus(StatusOrdemServico.ABERTO);
			ordemServico.setDataAbertura(OffsetDateTime.now());
		}

		return ordemServicoRepository.save(ordemServico);
	}

	public void excluir(Long id) {
		ordemServicoRepository.deleteById(id);
	}
}
