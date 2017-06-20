package br.com.frota.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import br.com.frota.model.Setor;
import br.com.frota.util.JPAUtil;

public class SetorDAO {

	public void gravar(Setor setor) {
		EntityManager em = new JPAUtil().getEntityManager();
		em.getTransaction().begin();
		em.persist(setor);
		em.getTransaction().commit();
		em.close();
	}

	public void atualizar(Setor setor) {
		EntityManager em = new JPAUtil().getEntityManager();
		em.getTransaction().begin();
		em.merge(setor);
		em.getTransaction().commit();
		em.close();
	}

	public void remover(Setor setor) {
		EntityManager em = new JPAUtil().getEntityManager();
		em.getTransaction().begin();
		em.remove(em.merge(setor));
		em.getTransaction().commit();
		em.close();
	}

	public Setor buscaSetor(Integer id) {
		EntityManager em = new JPAUtil().getEntityManager();
		return em.find(Setor.class, id);
	}

	public List<Setor> listarSetoresPorNome(String nome) {
		EntityManager em = new JPAUtil().getEntityManager();
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
