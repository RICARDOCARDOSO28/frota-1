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

	public Setor buscaSetor(int id) {
		Setor setor = em.find(Setor.class, id);
		return setor;
	}

	public List<Setor> listarSetoresPorNome(String nome) {
		TypedQuery<Setor> query = em.createQuery("select u from Setor u where u.nome like :pnome", Setor.class);
		query.setParameter("pnome", '%' + nome + '%');
		return query.getResultList();
	}

	public void remover(Setor setor) {
		em.getTransaction().begin();
		em.remove(em.merge(setor));
		em.getTransaction().commit();
	}

	public void atualizar(Setor setor) {
		em.getTransaction().begin();
		em.merge(setor);
		em.getTransaction().commit();
	}
	
	public void encerrar(){
		em.close();
	}

}
