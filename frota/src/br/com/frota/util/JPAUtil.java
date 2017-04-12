package br.com.frota.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPAUtil {

	private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("FrotaPU");

	public EntityManager getEntityManager() {
		EntityManager entityManager = emf.createEntityManager();
		return entityManager;
	}

	public void close(EntityManager em) {
		em.close();
	}
	
	
}
