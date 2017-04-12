package br.com.frota.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import br.com.frota.model.Agenda;
import br.com.frota.model.Usuario;
import br.com.frota.util.JPAUtil;

public class AgendaDAO {

	private EntityManager em;

	public AgendaDAO() {
		em = new JPAUtil().getEntityManager();
	}

	public void gravar(Agenda agenda, Integer usuarioId) {
		em.getTransaction().begin();
		Usuario usuario = em.find(Usuario.class, usuarioId);
		agenda.setUsuario(usuario);
		em.persist(agenda);
		em.getTransaction().commit();
	}

	public Agenda recuperarAgendaPorId(Integer id) {
		return em.find(Agenda.class, id);
	}
	
	public List<Agenda> listarAgendaPorNome(String nome) {
		TypedQuery<Agenda> query = em.createQuery("select a from Agenda a where a.usuario.nome like :pnome", Agenda.class);
		query.setParameter("pnome", '%' + nome + '%');
		return query.getResultList();
	}
	
	public List<Agenda> listarAgenda() {
		TypedQuery<Agenda> query = em.createQuery("select a from Agenda a", Agenda.class);
		return query.getResultList();
	}

}
