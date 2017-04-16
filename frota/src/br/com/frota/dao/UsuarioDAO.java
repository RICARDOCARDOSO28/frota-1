package br.com.frota.dao;

import java.util.Calendar;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;

import br.com.frota.model.Setor;
import br.com.frota.model.Usuario;
import br.com.frota.util.JPAUtil;
import br.com.frota.util.TipoUsuario;

public class UsuarioDAO {

	private EntityManager em;

	public UsuarioDAO() {
		em = new JPAUtil().getEntityManager();
	}

	public void adicionar(Usuario usuario, int setorId) {
		em.getTransaction().begin();
		Setor setor = em.find(Setor.class, setorId);
		setor.adicionarUsuario(usuario);		
		em.persist(usuario);
		em.getTransaction().commit();
	}

	public void atualizar(Usuario usuario, int setorId) {
		em.getTransaction().begin();
		usuario.getSetor().removerUsuarios(usuario);
		Setor setor = em.find(Setor.class, setorId);
		setor.adicionarUsuario(usuario);
		em.merge(usuario);
		em.getTransaction().commit();
	}

	public void remover(Usuario usuario) {
		em.getTransaction().begin();
		usuario.getSetor().removerUsuario(usuario);
		em.remove(em.merge(usuario));
		em.getTransaction().commit();
	}

	public Usuario buscarUsuario(Integer id) {
		return em.find(Usuario.class, id);
	}

	public List<Usuario> listarUsuariosPorNome(String nome, Integer setorId, TipoUsuario tipoUsuario) {
		String jpql = "select u from Usuario u where ";

		if (nome != null)
			jpql += "u.nome like :pnome and ";
		if (setorId != null)
			jpql += "u.setor.id = :psetor and ";
		if (tipoUsuario != null) {
			if (tipoUsuario == TipoUsuario.MOTORISTA_ALL) {
				jpql += "(u.cnhId != null or u.cnhId != '') and ";
				if (tipoUsuario == TipoUsuario.MOTORISTA_OK)
					jpql += "u.cnhValidade > :phoje and ";
			} else {
				jpql += "u.tipoUsuario = :ptipoUsuario and ";
			}
		}
		jpql += "1 = 1";

		TypedQuery<Usuario> query = em.createQuery(jpql, Usuario.class);

		if (nome != null)
			query.setParameter("pnome", '%' + nome + '%');
		if (setorId != null)
			query.setParameter("psetor", setorId);
		if (tipoUsuario != null) {
			if (tipoUsuario == TipoUsuario.MOTORISTA_ALL) {
				if (tipoUsuario == TipoUsuario.MOTORISTA_OK)
					query.setParameter("phoje", Calendar.getInstance().getTime(), TemporalType.DATE);
			} else {
				query.setParameter("ptipoUsuario", tipoUsuario);
			}
		}
		System.out.println(jpql);
		return query.getResultList();
	}

	public boolean usuarioExiste(Usuario usuario) {
		TypedQuery<Usuario> query = em.createQuery("select u from Usuario u where u.cpf = :pcpf and u.senha = :psenha",
				Usuario.class);
		query.setParameter("pcpf", usuario.getCpf());
		query.setParameter("psenha", usuario.getSenha());

		try {
			Usuario resultado = query.getSingleResult();
		} catch (NoResultException e) {
			return false;
		}
		return true;
	}

	public boolean usuarioCpfExiste(Usuario usuario) {
		TypedQuery<Usuario> query = em.createQuery("select u from Usuario u where u.cpf = :pcpf", Usuario.class);
		query.setParameter("pcpf", usuario.getCpf());

		if (query.getSingleResult() != null) {
			return true;
		} else {
			return false;
		}
	}

}
