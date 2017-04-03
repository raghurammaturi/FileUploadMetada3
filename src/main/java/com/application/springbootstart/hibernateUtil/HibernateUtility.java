/**
* The program implements the Hibernate Query.
* @author  Raghuram Maturi
* @version 1.0
* @since   2017-04-01 
*/
package com.application.springbootstart.hibernateUtil;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Component;

import com.application.springbootstart.filedata.FileData;

@Component
public class HibernateUtility {
	
/*	private SessionFactory sessionFactory;
	public  CriteriaBuilder builder;
	EntityManagerFactory factory;
	 EntityManager em;
	
	private SessionFactory hibernateFactory;

	  @Autowired
	  public void test(EntityManagerFactory factory) {
		  em = factory.createEntityManager();
		  builder = factory.getCriteriaBuilder();
		  System.out.println(" builder === "+builder);
	    if(factory.unwrap(SessionFactory.class) == null){
	      throw new NullPointerException("factory is not a hibernate factory");
	    }
	    this.sessionFactory = factory.unwrap(SessionFactory.class);
	  }*/
	  
	  
	public void readQuery(String fileName) {
		CriteriaBuilder builder = HibernateUtil.getCriteriaBuilder();
		System.out.println(" builder === "+builder);
		EntityManager em = HibernateUtil.getEntityManager();
		CriteriaQuery<FileData> criteriaQuery = builder.createQuery(FileData.class);
		Root<FileData> studentRoot = criteriaQuery.from(FileData.class);
		criteriaQuery.select(studentRoot);
		criteriaQuery.where(builder.equal(studentRoot.get("fileName"),fileName));
		List<FileData> f = em.createQuery(criteriaQuery).getResultList();
		for ( FileData files : f) {
		    System.out.println("id:"+files.getFileID()+", age:"+files.getFileName());
		}
		
		
		
		
/*		Query query = (Query) sessionFactory.openSession().createQuery("from file_data where file_name='"+fileName+"'");
		System.out.println("query ==  "+query);
		Integer i = query.getMaxResults();
		System.out.println("i=====================  "+i);*/
		
	}
	
	
}
