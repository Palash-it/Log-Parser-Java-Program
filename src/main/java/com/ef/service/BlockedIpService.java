package com.ef.service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.ef.dao.BlockedIpDAO;
import com.ef.model.BlockedIpModel;
import com.ef.model.LogRecordModel;

public class BlockedIpService {

	/**
	 * This method will take logrecord objectlist of ip and will cast them to
	 * blockedip object
	 * 
	 * @logrecordBlockedIp fetched on demand
	 * @logRecordSearchEndTime = endtime that used to find logip
	 * @threshold = how many request came from within given time
	 * @blockReason = reason why these ip will be blocked
	 */
	public List<BlockedIpModel> buildBlockedIpList(List<LogRecordModel> logRecordList, Date searchStarTime,
			Date logRecordSearchEndTime, int threshold, String blockReason) {
		try {
			if (logRecordList != null && logRecordList.size() > 0) {
				return logRecordList.stream().map(logip -> new BlockedIpModel(logip.getIp(), searchStarTime,
						logRecordSearchEndTime, logip.getThreshold(), blockReason)).collect(Collectors.toList());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<BlockedIpModel> getBlockedIpList(Date startTime, Date endTime, int threshold) {
		return new BlockedIpDAO().getList(startTime, endTime, threshold);
	}

}
