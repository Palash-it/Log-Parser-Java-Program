package com.ef.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "sequence")
public class SequenceModel implements Serializable{

	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "next_val")
	private Long nextVal;

	public SequenceModel() {
	}

	public SequenceModel(Long nextVal) {
		this.nextVal = nextVal;
	}

	public Long getNextVal() {
		return nextVal;
	}

	public void setNextVal(Long nextVal) {
		this.nextVal = nextVal;
	}

	@Override
	public String toString() {
		return "SequenceModel [nextVal=" + nextVal + "]";
	}
	
}
