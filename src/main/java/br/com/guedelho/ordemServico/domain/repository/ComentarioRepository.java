package br.com.guedelho.ordemServico.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.guedelho.ordemServico.domain.models.Comentario;

@Repository
public interface ComentarioRepository extends JpaRepository<Comentario, Long>{
	
	@Query("select c from Comentario c where c.ordemServico.id  =  :ordemServicoId")
	List<Comentario> findOrdemServicoId(@Param("ordemServicoId") Long ordemServicoId);

}
