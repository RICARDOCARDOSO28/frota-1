package br.com.frota.dao;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;

import br.com.frota.model.Agenda;
import br.com.frota.model.ControleCirculacao;
import br.com.frota.model.Usuario;
import br.com.frota.model.Veiculo;
import br.com.frota.util.JPAUtil;

public class ControleCirculacaoDAO {

	private EntityManager em;

	public ControleCirculacaoDAO() {
		em = new JPAUtil().getEntityManager();
	}

	public void gravar(ControleCirculacao controle, Integer agendaId, Integer veiculoId, Integer motoristaId) {
		em.getTransaction().begin();

		Usuario motorista = em.find(Usuario.class, motoristaId);
		Veiculo veiculo = em.find(Veiculo.class, veiculoId);
		Agenda agenda = em.find(Agenda.class, agendaId);

		motorista.adicionarControle(controle);
		veiculo.adicionarControle(controle);
		agenda.adicionarControle(controle);

		em.persist(controle);
		em.getTransaction().commit();
	}

	public void atualizar(ControleCirculacao controle, Integer agendaId, Integer veiculoId, Integer motoristaId) {
		em.getTransaction().begin();

		controle.getMotorista().removerControle(controle);
		controle.getVeiculo().removerControle(controle);
		controle.getAgenda().removerControle(controle);

		Usuario motorista = em.find(Usuario.class, motoristaId);
		Veiculo veiculo = em.find(Veiculo.class, veiculoId);
		Agenda agenda = em.find(Agenda.class, agendaId);

		motorista.adicionarControle(controle);
		veiculo.adicionarControle(controle);
		agenda.adicionarControle(controle);

		em.merge(controle);
		em.getTransaction().commit();
	}

	public void remover(ControleCirculacao controle) {
		em.getTransaction().begin();

		controle.getMotorista().removerControle(controle);
		controle.getVeiculo().removerControle(controle);
		controle.getAgenda().removerControle(controle);
		em.remove(em.merge(controle));

		em.getTransaction().commit();
	}

	public ControleCirculacao recuperarControleCirculacaoPorId(Integer id) {
		return em.find(ControleCirculacao.class, id);
	}

	public List<ControleCirculacao> listarControle(String solicitante, Integer veiculoId, Integer motoristaId,
			String veiculo, String destino, String motorista, Date dataInicial, Date dataFinal) {
		String jpql = "select c from ControleCirculacao c where ";

		if (solicitante != null)
			jpql += "c.agenda.usuario.nome like :psolicitante and ";
		if (veiculoId != null)
			jpql += "c.veiculo.id = :veiculoId and ";
		if (motoristaId != null)
			jpql += "c.motorista.id = :pmotoristaId and ";
		if (motorista != null)
			jpql += "c.motorista.nome like :pmotorista and ";
		if (veiculo != null)
			jpql += "c.veiculo.placa like :pveiculo and ";
		if (destino != null)
			jpql += "c.destino like :pdestino and ";

		if (dataInicial != null) {
			if (dataFinal == null) {
				dataFinal = Calendar.getInstance().getTime();
			}
			jpql += "(a.dataSaida between :pdataInicial and :pdataFinal) and (a.dataChegada between :pdataInicial and :pdataFinal) and ";
		}
		jpql += "1 = 1";

		TypedQuery<ControleCirculacao> query = em.createQuery(jpql, ControleCirculacao.class);

		if (solicitante != null)
			query.setParameter("psolicitante", '%' + solicitante + '%');
		if (veiculoId != null)
			query.setParameter("veiculoId", veiculoId);
		if (motoristaId != null)
			query.setParameter("pmotoristaId", motoristaId);
		if (motorista != null)
			query.setParameter("pmotorista", '%' + motorista + '%');
		if (veiculo != null)
			query.setParameter("pveiculo", '%' + veiculo + '%');
		if (destino != null)
			query.setParameter("pdestino", '%' + destino + '%');

		if (dataInicial != null) {
			query.setParameter("pdataInicial", dataInicial, TemporalType.DATE);
			query.setParameter("pdataFinal", dataFinal, TemporalType.DATE);
		}

		System.out.println(jpql);
		return query.getResultList();
	}
}
