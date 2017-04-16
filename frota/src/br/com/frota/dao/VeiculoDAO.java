package br.com.frota.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import br.com.frota.model.Veiculo;
import br.com.frota.util.JPAUtil;
import br.com.frota.util.TipoVeiculo;

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

	public List<Veiculo> listarVeiculosPorNome(String nome, Integer ano, TipoVeiculo veiculoTipo) {
		String jpql = "select v from Veiculo v where ";

		if (nome != null)
			jpql += "(v.placa like :pplaca or v.marca like :pmarca or v.modelo like :pmodelo) and ";
		if (ano != null)
			jpql += "v.ano = :pano and ";
		if (veiculoTipo != null)
			jpql += "v.tipoVeiculo = :pveiculo and ";
		jpql += "1 = 1";

		TypedQuery<Veiculo> query = em.createQuery(jpql, Veiculo.class);

		if (nome != null) {
			query.setParameter("pplaca", '%' + nome + '%');
			query.setParameter("pmarca", '%' + nome + '%');
			query.setParameter("pmodelo", '%' + nome + '%');
		}
		if (ano != null)
			query.setParameter("pano", ano);
		if (veiculoTipo != null)
			query.setParameter("pveiculo", veiculoTipo);
		
		System.out.println(jpql);
		return query.getResultList();
	}

	public void remover(Veiculo veiculo) {
		em.getTransaction().begin();
		em.remove(em.merge(veiculo));
		em.getTransaction().commit();
	}

}
