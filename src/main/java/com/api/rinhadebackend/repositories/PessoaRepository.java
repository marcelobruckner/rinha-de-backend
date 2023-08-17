package com.api.rinhadebackend.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.api.rinhadebackend.models.Pessoa;

public interface PessoaRepository extends JpaRepository<Pessoa, UUID> {

	@Query(nativeQuery = true, value = "select  distinct p.* from pessoas p "
			+ "left join pessoas_stacks ps on (p.id = ps.pessoa_id) "
			+ "where lower(p.apelido) LIKE lower(concat('%', :searchTerm , '%')) "
			+ "or lower(p.nome) LIKE lower(concat('%', :searchTerm , '%')) "
			+ "or lower(ps.stack) LIKE lower(concat('%', :searchTerm , '%')) " + "limit 50")
	List<Pessoa> findAllBySearchTerm(String searchTerm);
}
