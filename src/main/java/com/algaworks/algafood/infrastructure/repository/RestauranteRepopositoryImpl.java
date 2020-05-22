package com.algaworks.algafood.infrastructure.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.model.repository.RestauranteRepository;

@Repository
public class RestauranteRepopositoryImpl implements RestauranteRepository {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public List<Restaurante> listar() {
		return entityManager.createQuery("from Restaurante", Restaurante.class).getResultList();
	}

	@Override
	public Restaurante buscar(Long id) {
		return entityManager.find(Restaurante.class, id);
	}

	@Override
	@Transactional
	public Restaurante salvar(Restaurante restaurante) {
		return entityManager.merge(restaurante);
	}

	@Override
	@Transactional
	public void remover(Long id) {
		Restaurante restaurante = buscar(id);
		
		if (restaurante == null) {
			throw new EmptyResultDataAccessException(1);
		}
		
		entityManager.remove(restaurante);
	}

}
