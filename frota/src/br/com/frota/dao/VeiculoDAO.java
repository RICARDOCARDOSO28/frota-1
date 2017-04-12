package br.com.frota.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import br.com.frota.model.Veiculo;
import br.com.frota.util.JPAUtil;

public class VeiculoDAO {

	private EntityManager em;

	public VeiculoDAO() {
		em = new JPAUtil().getEntityManager();
	}

	public void gravar(Veiculo veiculo) {
		em.getTransaction().begin();
		em.persist(veiculo);
		em.getTransaction().commit();
	}

	public Veiculo recuperarVeiculoPorId(int id) {
		return em.find(Veiculo.class, id);
	}

	public List<Veiculo> listarVeiculosPorNome(String nome) {
		TypedQuery<Veiculo> query = em.createQuery("select v from Veiculo v where v.placa like :pnome or v.marca like :pnome or v.marca like :pnome", Veiculo.class);
		query.setParameter("pnome", '%' + nome + '%');
		return query.getResultList();
	}

	public void remover(Veiculo veiculo) {
		em.getTransaction().begin();
		em.remove(em.merge(veiculo));
		em.getTransaction().commit();
	}

}
