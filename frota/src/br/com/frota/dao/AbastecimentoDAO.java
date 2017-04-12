package br.com.frota.dao;

import java.util.List;

import javax.persistence.EntityManager;
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
		abastecimento.setUsuario(usuario);
		abastecimento.setVeiculo(veiculo);
		em.persist(abastecimento);
		em.getTransaction().commit();
	}

	public Abastecimento recuperarAbastecimentoPorId(Integer id) {
		return em.find(Abastecimento.class, id);
	}

	public List<Abastecimento> listarAbastecimentosPorNome(String nome) {
		TypedQuery<Abastecimento> query = em.createQuery("select a.usuario.nome, a.veiculo.placa, a.qntCombustivel, a.dataAbastecimento  from Abastecimento a where a.usuario.nome like :pnome",
				Abastecimento.class);
		query.setParameter("pnome", '%' + nome + '%');
		return query.getResultList();
	}

	public void remover(Abastecimento abastecimento) {
		em.getTransaction().begin();
		em.remove(em.merge(abastecimento));
		em.getTransaction().commit();
	}

}
