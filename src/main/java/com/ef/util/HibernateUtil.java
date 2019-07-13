package com.ef.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

	private static SessionFactory sessionFactory = buildSessionFactory();

	private static SessionFactory buildSessionFactory() {
		try {
			Configuration config = new Configuration();
			config.configure();
			sessionFactory = config.buildSessionFactory();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sessionFactory;
	}

	public static SessionFactory getSessionFactory() {
		if(sessionFactory == null) {
			return buildSessionFactory();
		}
		return sessionFactory;
	}
}
