package br.com.frota.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.SetJoin;

import br.com.frota.model.Setor;
import br.com.frota.model.Usuario;
import br.com.frota.util.JPAUtil;

public class UsuarioDAO {

	private EntityManager em;

	public UsuarioDAO() {
		em = new JPAUtil().getEntityManager();
	}

	public void adicionar(Usuario usuario, int setorId) {
		em.getTransaction().begin();
		Setor setor = em.find(Setor.class, setorId);
		usuario.setSetor(setor);
		em.persist(usuario);
		em.getTransaction().commit();
	}

	public void atualizar(Usuario usuario, int setorId) {
		em.getTransaction().begin();
		Setor setor = em.find(Setor.class, setorId);
		usuario.setSetor(setor);
		em.merge(usuario);
		em.getTransaction().commit();
	}

	public void remover(Usuario usuario) {
		em.getTransaction().begin();
		em.remove(em.merge(usuario));
		em.getTransaction().commit();
	}

	public Usuario buscarUsuario(int id) {
		Usuario usuario = em.find(Usuario.class, id);
		return usuario;
	}

	public List<Usuario> listarUsuariosPorFiltros(String nome, Integer setorId) {
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<Usuario> query = criteriaBuilder.createQuery(Usuario.class);
		Root<Usuario> root = query.from(Usuario.class);

		Path<String> nomePath = root.<String>get("nome");
		Path<String> cpfPath = root.<String>get("nome");
		Join<Usuario, Setor> setorPath = root.join("setor", JoinType.LEFT);
		// Path<Integer> setorPath = root.join("setor").<Integer>get("id");
		// SetJoin<Usuario, Setor> setorPath =
		// root.join(root.getModel().getSet("setor", Setor.class));

		Path<String> fonePath = root.<String>get("nome");

		List<Predicate> predicates = new ArrayList<>();

		if (nome.trim().length() > 2) {
			Predicate nomeIgual = criteriaBuilder.like(nomePath, nome);
			predicates.add(nomeIgual);
			Predicate cpfIgual = criteriaBuilder.like(cpfPath, nome);
			predicates.add(cpfIgual);
		}
		if (nome.trim().length() > 7) {
			Predicate foneIgual = criteriaBuilder.equal(fonePath, nome);
			predicates.add(foneIgual);
		}
		if (nome.trim().length() > 10) {
			Predicate cpfIgual = criteriaBuilder.like(cpfPath, nome);
			predicates.add(cpfIgual);
		}
		if (setorId != null) {
			Predicate setorIdIgual = criteriaBuilder.equal(setorPath, setorId);
			predicates.add(setorIdIgual);
		}

		query.where((Predicate[]) predicates.toArray());

		return em.createQuery(query).getResultList();
	}

	// public List<Usuario> listarUsuarios() {
	// CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
	// CriteriaQuery<Usuario> criteriaQuery =
	// criteriaBuilder.createQuery(Usuario.class);
	// CriteriaQuery<Usuario> select =
	// criteriaQuery.select(criteriaQuery.from(Usuario.class));
	// TypedQuery<Usuario> query = em.createQuery(select);
	// return query.getResultList();
	// }

	public List<Usuario> listarUsuariosPorNome(String nome) {
		TypedQuery<Usuario> query = em.createQuery("select u from Usuario u where u.nome like :pnome", Usuario.class);
		query.setParameter("pnome", '%' + nome + '%');
		return query.getResultList();
	}

	// public List<Usuario> listarUsuariosPorNome(String nome, Integer setor) {
	// TypedQuery<Usuario> query = em.createQuery("select u from Usuario u where
	// u.nome like :pnome JOIN FETCH Setor s on (s.id = :psetor)",
	// Usuario.class);
	// query.setParameter("pnome", '%' + nome + '%');
	// query.setParameter("psetor", setor);
	// return query.getResultList();
	// }

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

	public List<Usuario> listarMotoristas() {
		// Comparar se CNH ainda esta dentro da validade
		TypedQuery<Usuario> query = em.createQuery("select u from Usuario u where u.cnhId != null", Usuario.class);
		return query.getResultList();
	}

	public List<Usuario> listarAdministradores() {
		TypedQuery<Usuario> query = em.createQuery("select u from Usuario u where u.adminStatus < 2", Usuario.class);
		return query.getResultList();
	}

}
