package br.com.frota.dao;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;

import br.com.frota.model.Abastecimento;
import br.com.frota.model.Usuario;
import br.com.frota.model.Veiculo;
import br.com.frota.util.JPAUtil;

public class AbastecimentoDAO {

	private EntityManager em;

	public AbastecimentoDAO() {
		em = new JPAUtil().getEntityManager();
	}

	public void gravar(Abastecimento abastecimento, Integer usuarioId, Integer veiculoId) {
		em.getTransaction().begin();

		Usuario usuario = em.find(Usuario.class, usuarioId);
		Veiculo veiculo = em.find(Veiculo.class, veiculoId);
		
		usuario.adicionarAbastecimento(abastecimento);
		veiculo.adicionarAbastecimento(abastecimento);

		em.persist(abastecimento);
		em.getTransaction().commit();
	}
	
	public void atualizar(Abastecimento abastecimento, Integer usuarioId, Integer veiculoId) {
		em.getTransaction().begin();
		
		abastecimento.getUsuario().removerAbastecimento(abastecimento);
		abastecimento.getVeiculo().removerAbastecimento(abastecimento);
		
		Usuario usuario = em.find(Usuario.class, usuarioId);
		Veiculo veiculo = em.find(Veiculo.class, veiculoId);
		
		usuario.adicionarAbastecimento(abastecimento);
		veiculo.adicionarAbastecimento(abastecimento);

		em.merge(abastecimento);
		em.getTransaction().commit();
	}

	public Abastecimento recuperarAbastecimentoPorId(Integer id) {
		return em.find(Abastecimento.class, id);
	}

	public List<Abastecimento> listarAbastecimentosPorNome(String nome, Date dataInicial, Date dataFinal) {
		String jpql = "select a from Abastecimento a where ";

		if (nome != null) {
			jpql += "(a.usuario.nome like :pnome or a.veiculo.placa like :pplaca) and ";
		}
		
		if (dataInicial != null) {
			if (dataFinal == null) {
				dataFinal = Calendar.getInstance().getTime();
			}
			jpql += "a.dataAbastecimento between :pdataInicial and :pdataFinal and ";
		}
		jpql += "1 = 1";

		TypedQuery<Abastecimento> query = em.createQuery(jpql, Abastecimento.class);

		if (nome != null) {
			query.setParameter("pnome", '%' + nome + '%');
			query.setParameter("pplaca", '%' + nome + '%');
		}
		if (dataInicial != null) {
			query.setParameter("pdataInicial", dataInicial, TemporalType.DATE);
			query.setParameter("pdataFinal", dataFinal, TemporalType.DATE);
		}
		
		System.out.println(jpql);
		return query.getResultList();
	}

	public void remover(Abastecimento abastecimento) {
		em.getTransaction().begin();
		abastecimento.getUsuario().removerAbastecimento(abastecimento);
		abastecimento.getVeiculo().removerAbastecimento(abastecimento);
		em.remove(em.merge(abastecimento));
		em.getTransaction().commit();
	}

}
