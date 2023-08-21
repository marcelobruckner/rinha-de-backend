package com.api.rinhadebackend.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.api.rinhadebackend.models.Pessoa;

public interface PessoaRepository extends JpaRepository<Pessoa, UUID> {

	@Query(nativeQuery = true, value = "select p.* from pessoas p " +
			"where lower(p.apelido) like %:term% " +
			"or lower(p.nome) like %:term% " +
			"or lower(p.stack) like %:term% " +
			"LIMIT 50")
	List<Pessoa> findAllBySearchTerm(@Param("term") String term);

	Pessoa findByApelido(String apelido);
}
