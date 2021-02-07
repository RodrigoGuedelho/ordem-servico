package br.com.guedelho.ordemServico.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.guedelho.ordemServico.domain.models.Cliente;


@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long>{

	@Query(value = "SELECT c FROM Cliente c where lower(c.nome) like %:nome%")
    List<Cliente> findByNome(@Param("nome") Optional<String> nome);
	Cliente findByEmail(String email);
}
