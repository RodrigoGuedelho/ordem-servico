package br.com.guedelho.ordemServico.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.guedelho.ordemServico.domain.models.OrdemServico;
import br.com.guedelho.ordemServico.domain.models.StatusOrdemServico;

@Repository
public interface OrdemServicoRepository extends JpaRepository<OrdemServico, Long> {
	@Query(value = "select o from OrdemServico o where o.status = :status and lower(o.descricao) like %:descricao%  and  o.cliente.id = :clienteId ")
	List<OrdemServico> find(@Param("status") StatusOrdemServico status, @Param("descricao") String descricao, @Param("clienteId") Long clienteId);
	
	@Query(value = "select o from OrdemServico o where o.status = :status and lower(o.descricao) like %:descricao%  ")
	List<OrdemServico> find(@Param("status") StatusOrdemServico status, @Param("descricao") String descricao);
	
	@Query(value = "select o from OrdemServico o where o.status = :status and  o.cliente.id  =  :clienteId ")
	List<OrdemServico> find(@Param("status") StatusOrdemServico status, @Param("clienteId") Long clienteId);
	
	@Query(value = "select o from OrdemServico o where o.status = :status ")
	List<OrdemServico> find(@Param("status") StatusOrdemServico status);
}
