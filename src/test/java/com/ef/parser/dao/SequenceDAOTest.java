package com.ef.parser.dao;

import static org.junit.Assert.assertNotEquals;

import org.junit.Before;
import org.junit.Test;

import com.ef.dao.SequenceDAO;

public class SequenceDAOTest {

	private SequenceDAO seqDao;
	@Before
	public void setUp() {
		seqDao = new SequenceDAO();
	}
	
	@Test
	public void TestGetNextVal() {
		long nextVal = seqDao.getNextVal();
		System.out.println(nextVal);
		assertNotEquals(nextVal, 0);
	}
}
