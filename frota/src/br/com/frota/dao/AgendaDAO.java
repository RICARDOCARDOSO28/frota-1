package br.com.frota.dao;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;

import br.com.frota.model.Agenda;
import br.com.frota.model.Usuario;
import br.com.frota.util.JPAUtil;
import br.com.frota.util.StatusAgenda;
import br.com.frota.util.TipoVeiculo;

public class AgendaDAO {

	private EntityManager em;

	public AgendaDAO() {
		em = new JPAUtil().getEntityManager();
	}

	public void gravar(Agenda agenda, Integer usuarioId) {
		em.getTransaction().begin();
		agenda.setStatusAgenda(StatusAgenda.EM_ABERTO);
		Usuario usuario = em.find(Usuario.class, usuarioId);
		usuario.adicionarAgenda(agenda);
		em.persist(agenda);
		em.getTransaction().commit();
	}

	public void atualizar(Agenda agenda, Integer usuarioId, StatusAgenda statusAgenda) {

		System.out.println("usuarioid=" + usuarioId + ", agenda.usuario.id=" + agenda.getUsuario().getId() + ", status"
				+ statusAgenda);
		em.getTransaction().begin();
		agenda.setStatusAgenda(statusAgenda);
		agenda.getUsuario().removerAgenda(agenda);
		// if (usuarioId == null)
		// usuarioId = agenda.getUsuario().getId();

		Usuario usuario = em.find(Usuario.class, usuarioId);
		usuario.adicionarAgenda(agenda);
		em.merge(agenda);
		em.getTransaction().commit();
	}

	public void remover(Agenda agenda) {
		em.getTransaction().begin();
		agenda.getUsuario().removerAgenda(agenda);
		em.remove(em.merge(agenda));
		em.getTransaction().commit();
	}

	public Agenda recuperarAgendaPorId(Integer id) {
		return em.find(Agenda.class, id);
	}

	public List<Agenda> listarAgenda(String agendaNome, Date dataInicial, Date dataFinal, TipoVeiculo tipoVeiculo,
			StatusAgenda statusAgenda) {
		String jpql = "select a from Agenda a where ";

		if (agendaNome != null) {
			jpql += "a.usuario.nome like :pusuario and ";
		}
		if (tipoVeiculo != null) {
			jpql += "a.tipoVeiculo = :ptipoVeiculo and ";
		}
		if (statusAgenda != null) {
			jpql += "a.statusAgenda = :pstatusAgenda and ";
		}
		if (dataInicial != null) {
			if (dataFinal == null) {
				dataFinal = Calendar.getInstance().getTime();
			}
			jpql += "((a.dataChegada between :pdataInicial and :pdataFinal) and (a.dataSaida between :pdataInicial and :pdataFinal)) and ";
		}
		jpql += "1 = 1";

		TypedQuery<Agenda> query = em.createQuery(jpql, Agenda.class);

		if (agendaNome != null)
			query.setParameter("pusuario", '%' + agendaNome + '%');
		if (tipoVeiculo != null)
			query.setParameter("ptipoVeiculo", tipoVeiculo);
		if (statusAgenda != null)
			query.setParameter("pstatusAgenda", statusAgenda);

		if (dataInicial != null) {
			query.setParameter("pdataInicial", dataInicial, TemporalType.DATE);
			query.setParameter("pdataFinal", dataFinal, TemporalType.DATE);
		}

		System.out.println(jpql);
		return query.getResultList();
	}
}
