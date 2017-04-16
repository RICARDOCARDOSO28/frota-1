package br.com.frota.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import br.com.frota.model.Setor;
import br.com.frota.util.JPAUtil;

public class SetorDAO {

	private EntityManager em;

	public SetorDAO() {
		em = new JPAUtil().getEntityManager();
	}

	public void gravar(Setor setor) {
		em.getTransaction().begin();
		em.persist(setor);
		em.getTransaction().commit();
	}

	public void atualizar(Setor setor) {
		em.getTransaction().begin();
		em.merge(setor);
		em.getTransaction().commit();
	}

	public void remover(Setor setor) {
		em.getTransaction().begin();
		em.remove(em.merge(setor));
		em.getTransaction().commit();
	}

	public Setor buscaSetor(Integer id) {
		return em.find(Setor.class, id);
	}

	public List<Setor> listarSetoresPorNome(String nome) {
		String jpql = "select s from Setor s where s.nome like :pnome or s.fone like :pfone";

		TypedQuery<Setor> query = em.createQuery(jpql, Setor.class);

		query.setParameter("pnome", '%' + nome + '%');
		query.setParameter("pfone", '%' + nome + '%');

		System.out.println(jpql);
		return query.getResultList();
	}

	public Integer contarUsuarios(Setor setor){
		return setor.getUsuarios().size();
	}
}
