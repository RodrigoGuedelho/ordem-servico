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
    List<Cliente> findByNome(@Param("nome") String nome);
	Cliente findByEmail(String email);
	@Query(value = "select c from Cliente c where lower(c.nome) like :nome and lower(c.email) like :email and lower(c.telefone) like :telefone")
	List<Cliente> find(@Param("nome") String nome, @Param("email") String email, @Param("telefone") String telefone);
	
	
}
