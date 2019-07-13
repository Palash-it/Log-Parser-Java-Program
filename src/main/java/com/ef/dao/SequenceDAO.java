package com.ef.dao;

import org.hibernate.Session;
import org.hibernate.query.Query;

import com.ef.model.SequenceModel;
import com.ef.util.ApplicationConstants;
import com.ef.util.HibernateUtil;

public class SequenceDAO {

	/**
	 * This method is a resource share method so made it synchronize It will return
	 * current nextVal and will update after increment by total size of rows that
	 * can insert in a single transaction
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public synchronized long getNextVal() {
		long nextSeq = 0;
		Session hbSession = HibernateUtil.getSessionFactory().openSession();
		try {
			hbSession.getTransaction().begin();
			String sql = "FROM SequenceModel";
			Query<SequenceModel> query = hbSession.createQuery(sql);
			SequenceModel sequence = query.getSingleResult();
			nextSeq = sequence.getNextVal();

			sql = "UPDATE SequenceModel SET nextVal = " + (nextSeq + ApplicationConstants.ROWS_INSERT_PER_TRANSACTION);
			hbSession.createQuery(sql).executeUpdate();
			hbSession.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			hbSession.close();
		}
		return nextSeq;
	}
}
