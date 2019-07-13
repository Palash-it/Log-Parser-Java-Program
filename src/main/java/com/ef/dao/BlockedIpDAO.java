package com.ef.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;

import com.ef.model.BlockedIpModel;
import com.ef.util.ApplicationConstants;
import com.ef.util.HibernateUtil;

public class BlockedIpDAO {

	@SuppressWarnings("unchecked")
	public List<BlockedIpModel> getList(Date startTime, Date endTime, int threshold) {
		Session hbSession = HibernateUtil.getSessionFactory().openSession();
		List<BlockedIpModel> list = new ArrayList<>();
		try {
			hbSession.beginTransaction();
			String queryString = "FROM BlockedIpModel  WHERE startTime = :startTime "
					+ " AND endTime =:endTime AND threshold > :threshold";
			Query<BlockedIpModel> query = hbSession.createQuery(queryString);
			query.setParameter("startTime", startTime);
			query.setParameter("endTime", endTime);
			query.setParameter("threshold", threshold);
			list = query.list();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			hbSession.flush();
			hbSession.close();
		}
		return list;
	}

	public void insertBatch(List<BlockedIpModel> ipList) {
		if (ipList != null && ipList.size() > 0) {
			Session hbSession = HibernateUtil.getSessionFactory().openSession();
			try {
				hbSession.beginTransaction();
				for (int i = 0; i < ipList.size(); i++) {
					hbSession.save(ipList.get(i));
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
	
}
