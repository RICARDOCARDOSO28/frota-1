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
		
		System.out.println("usuarioid="+usuarioId+", agenda.usuario.id=" +agenda.getUsuario().getId()+", status"+statusAgenda);
		em.getTransaction().begin();
		agenda.setStatusAgenda(statusAgenda);
		agenda.getUsuario().removerAgenda(agenda);
//		if (usuarioId == null)
//			usuarioId = agenda.getUsuario().getId();

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

	public List<Agenda> listarAgenda(String nome, Date dataInicial, Date dataFinal, StatusAgenda status) {
		String jpql = "select a from Agenda a where ";

		if (nome != null) {
			jpql += "(a.usuario.nome like :pnome or a.destino like :pdestino or a.tipoVeiculo.label like :ptipoVeiculo) and ";
		}

		if (dataInicial != null) {
			if (dataFinal == null) {
				dataFinal = Calendar.getInstance().getTime();
			}
			jpql += "(a.dataSaida between :pdataInicial and :pdataFinal) and (a.dataChegada between :pdataInicial and :pdataFinal) and ";
		}
		if (status != null) {
			jpql += "a.statusAgenda = :pstatus and ";
		}
		jpql += "1 = 1";

		TypedQuery<Agenda> query = em.createQuery(jpql, Agenda.class);

		if (nome != null) {
			query.setParameter("pnome", '%' + nome + '%');
			query.setParameter("pdestino", '%' + nome + '%');
			query.setParameter("ptipoVeiculo", '%' + nome + '%');
		}
		if (dataInicial != null) {
			query.setParameter("pdataInicial", dataInicial, TemporalType.DATE);
			query.setParameter("pdataFinal", dataFinal, TemporalType.DATE);
		}
		if (status != null)
			query.setParameter("pstatus", status);

		System.out.println(jpql);
		return query.getResultList();
	}

}
