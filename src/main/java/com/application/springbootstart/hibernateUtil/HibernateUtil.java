/**
* The program implements the PersistenceManager.
* @author  Raghuram Maturi
* @version 1.0
* @since   2017-04-01 
*/

package com.application.springbootstart.hibernateUtil;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
public class HibernateUtil {
	
	@PersistenceContext
 	private static final EntityManagerFactory emFactory;
	
	static {
		   emFactory = Persistence.createEntityManagerFactory("com.application");
	}
	public static CriteriaBuilder getCriteriaBuilder(){
		CriteriaBuilder builder = emFactory.getCriteriaBuilder();
		return  builder;
	}
	public static EntityManager getEntityManager(){
		return emFactory.createEntityManager();
	}
} 
