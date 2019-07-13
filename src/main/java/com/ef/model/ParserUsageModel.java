package com.ef.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "parser_usage")
public class ParserUsageModel implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "log_file_path")
	private String logFilePath;
	@Column(name = "status")
	private String status;
	@Column(name = "time_taken")
	private Long timeTaken;

	public ParserUsageModel() {
	}

	public ParserUsageModel(String logFilePath, String status, Long timeTaken) {
		this.logFilePath = logFilePath;
		this.status = status;
		this.timeTaken = timeTaken;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLogFilePath() {
		return logFilePath;
	}

	public void setLogFilePath(String logFilePath) {
		this.logFilePath = logFilePath;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getTimeTaken() {
		return timeTaken;
	}

	public void setTimeTaken(Long timeTaken) {
		this.timeTaken = timeTaken;
	}

	@Override
	public String toString() {
		return "ParserUsageModel [id=" + id + ", logFilePath=" + logFilePath + ", status=" + status + ", timeTaken="
				+ timeTaken + "]";
	}

}
