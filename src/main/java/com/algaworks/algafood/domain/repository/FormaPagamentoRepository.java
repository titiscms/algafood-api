package com.algaworks.algafood.domain.repository;

import java.time.OffsetDateTime;

import org.springframework.data.jpa.repository.Query;

import com.algaworks.algafood.domain.model.FormaPagamento;

public interface FormaPagamentoRepository extends CustomJpaRepository<FormaPagamento, Long> {

	@Query("select max(dataAtualizacao) from FormaPagamento")
	OffsetDateTime getDataUltimaAtualizacao();

	@Query("select dataAtualizacao from FormaPagamento where id = :id")
	OffsetDateTime getDataUltimaAtualizacaoById(Long id);
}
