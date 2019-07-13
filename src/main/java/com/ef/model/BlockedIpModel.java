package com.ef.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "blocked_ip")
public class BlockedIpModel implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String ip;
	@Column(name = "start_date_time")
	private Date startTime;
	@Column(name = "end_date_time")
	private Date endTime;
	@Column(name = "threshold")
	private int threshold;
	@Column(name = "blocked_reason")
	private String blockedReason;
	@Column(name = "creation_date_time")
	private Date creationTime;

	public BlockedIpModel() {
	}

	public BlockedIpModel(String ip, Date startTime, Date endTime, int threshold, String blockedReason) {
		this.ip = ip;
		this.startTime = startTime;
		this.endTime = endTime;
		this.threshold = threshold;
		this.blockedReason = blockedReason;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public int getThreshold() {
		return threshold;
	}

	public void setThreshold(int threshold) {
		this.threshold = threshold;
	}

	public String getBlockedReason() {
		return blockedReason;
	}

	public void setBlockedReason(String blockedReason) {
		this.blockedReason = blockedReason;
	}

	public Date getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}

	@Override
	public String toString() {
		return "BlockedIpModel [id=" + id + ", ip=" + ip + ", startTime=" + startTime + ", endTime=" + endTime
				+ ", threshold=" + threshold + ", blockedReason=" + blockedReason + ", creationTime=" + creationTime
				+ "]";
	}

}
