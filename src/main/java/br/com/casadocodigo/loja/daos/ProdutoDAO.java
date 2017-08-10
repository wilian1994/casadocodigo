package br.com.casadocodigo.loja.daos;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import br.com.casadocodigo.loja.models.Produto;

@Repository
@Transactional
public class ProdutoDAO {

	@PersistenceContext
	private EntityManager manager;

	public void gravar(Produto produto){
		manager.persist(produto);
	}

	public List<Produto> listar() {
		return manager.createQuery("Select p from Produto p", Produto.class).getResultList();
	}

	public Produto find(Integer id) {
		return manager.createQuery("select distinct(p) from Produto p "
				+ "join fetch p.precos precos where p.id = :id", Produto.class).setParameter("id",id).getSingleResult(); //setParameter, passa as informações no where e o singleResult realiza os relacionamento.
	}

}
