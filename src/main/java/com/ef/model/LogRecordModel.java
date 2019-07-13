package com.ef.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "log_records")
public class LogRecordModel implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	private Long id;
	@Column(name = "date_time")
	private Date dateTimeStamp;
	private String ip;
	private String request;
	private int status;
	@Column(name = "user_agent")
	private String userAgent;
	@Transient
	private int threshold;

	public LogRecordModel() {
	}

	public LogRecordModel(int threshold, String ip) {
		this.threshold = threshold;
		this.ip = ip;
	}

	public LogRecordModel(Date dateTimeStamp, String ip, String request, int status, String userAgent) {
		this.dateTimeStamp = dateTimeStamp;
		this.ip = ip;
		this.request = request;
		this.status = status;
		this.userAgent = userAgent;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDateTimeStamp() {
		return dateTimeStamp;
	}

	public void setDateTimeStamp(Date dateTimeStamp) {
		this.dateTimeStamp = dateTimeStamp;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getRequest() {
		return request;
	}

	public void setRequest(String request) {
		this.request = request;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getUserAgent() {
		return userAgent;
	}

	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}

	public int getThreshold() {
		return threshold;
	}

	public void setThreshold(int threshold) {
		this.threshold = threshold;
	}

	@Override
	public String toString() {
		return "LogRecordModel [id=" + id + ", dateTimeStamp=" + dateTimeStamp + ", ip=" + ip + ", request=" + request
				+ ", status=" + status + ", userAgent=" + userAgent + ", threshold=" + threshold + "]";
	}
}
