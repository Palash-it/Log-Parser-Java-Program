package com.ef.dao;

import org.hibernate.Session;
import org.hibernate.query.Query;

import com.ef.model.ParserUsageModel;
import com.ef.util.HibernateUtil;

public class ParserUsageDAO {

	@SuppressWarnings("unchecked")
	public long getCountByFilePath(String logFilePath) {
		Session hbSession = HibernateUtil.getSessionFactory().openSession();
		try {
			hbSession.beginTransaction();
			String queryString = "SELECT COUNT(*) FROM ParserUsageModel WHERE logFilePath =: logFilePath ";
			Query<Long> query = hbSession.createQuery(queryString);
			query.setParameter("logFilePath", logFilePath);
			return (long) query.getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			hbSession.flush();
			hbSession.close();
		}
		return 0;
	}

	/**
	 * Single Insert
	 */
	public void insert(ParserUsageModel usage) {
		Session hbSession = HibernateUtil.getSessionFactory().openSession();
		try {
			hbSession.beginTransaction();
			hbSession.save(usage);
			hbSession.getTransaction().commit();
		} catch (RuntimeException e) {
			if (hbSession.getTransaction() != null) {
				hbSession.getTransaction().rollback();
			}
			e.printStackTrace();
		} finally {
			hbSession.close();
		}
	}
}
