package com.ef.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.Session;
import org.hibernate.query.Query;

import com.ef.model.LogRecordModel;
import com.ef.util.ApplicationConstants;
import com.ef.util.HibernateUtil;
import com.ef.util.ParserUtil;

public class LogRecordDAO {

	public void insertBatch(List<LogRecordModel> logList) {
		if (logList != null && logList.size() > 0) {
			long idSeq = new SequenceDAO().getNextVal();
			Session hbSession = HibernateUtil.getSessionFactory().openSession();
			try {
				hbSession.beginTransaction();
				for (int i = 0; i < logList.size(); i++) {
					idSeq++;
					LogRecordModel log = logList.get(i);
					log.setId(idSeq);
					hbSession.save(log);
					if (i % ApplicationConstants.BATCH_SIZE == 0) {
						hbSession.flush();
						hbSession.clear();
					}
				}
				hbSession.getTransaction().commit();
			} catch (Exception e) {
				if (hbSession.getTransaction() != null)
					hbSession.getTransaction().rollback();
				e.printStackTrace();
			} finally {
				hbSession.close();
			}
		}
	}

	@SuppressWarnings("unchecked")
	public List<LogRecordModel> getLogIpList(Date startDate, Date endDate, int threshold) {
		Session hbSession = HibernateUtil.getSessionFactory().openSession();
		List<Object[]> list = new ArrayList<>();
		try {
			hbSession.beginTransaction();
			String queryString = "SELECT COUNT(id) as threshold,ip FROM LogRecordModel  WHERE dateTimeStamp BETWEEN "
					+ " :startDate AND :endDate GROUP BY ip HAVING COUNT(id) >= :threshold";
			Query<Object[]> query = hbSession.createQuery(queryString);
			query.setParameter("startDate", startDate);
			query.setParameter("endDate", endDate);
			query.setParameter("threshold", (long) threshold);
			list = query.getResultList();
			if (list != null && list.size() > 0) {
				return list.stream().map(obj -> new LogRecordModel(ParserUtil.parseInt("" + obj[0]), "" + obj[1]))
						.collect(Collectors.toList());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			hbSession.flush();
			hbSession.close();
		}
		return null;
	}
}
