package com.ef.service;

import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

import com.ef.dao.BlockedIpDAO;
import com.ef.dao.LogRecordDAO;
import com.ef.dao.ParserUsageDAO;
import com.ef.model.BlockedIpModel;
import com.ef.model.LogRecordModel;
import com.ef.model.ParserUsageModel;
import com.ef.util.ApplicationConstants;
import com.ef.util.ParserUtil;

public class ParserService {

	public static final long ONE_HOUR = 3600000;

	/**
	 * Read log file from specific path and make a arraylist of object Send to store
	 * the list after reaching at least 500 entries
	 */
	public boolean readAndSaveRecordFromLogFile(String filePath) {
		ArrayList<LogRecordModel> recordList = new ArrayList<>();
		LogRecordDAO logDao = new LogRecordDAO();
		try (Stream<String> lines = Files.lines(Paths.get(filePath))) {
			lines.forEach(line -> {
				String temp[] = line.split("\\|");
				if (temp.length >= 5) {
					try {
						LogRecordModel record = new LogRecordModel(Timestamp.valueOf(temp[0]), temp[1], temp[2],
								ParserUtil.parseInt(temp[3]), temp[4]);
						recordList.add(record);
						if (recordList.size() % ApplicationConstants.ROWS_INSERT_PER_TRANSACTION == 0) {
							logDao.insertBatch(recordList);
							recordList.clear();
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
			if (recordList.size() > 0) {
				logDao.insertBatch(recordList);
				recordList.clear();
			}
			lines.close();
		} catch (FileNotFoundException e) {
			System.out.println("File not found.");
			System.exit(0);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
		return true;
	}

	/**
	 * This method will parse and store log file if its already not parsed
	 * 
	 * @param filePath
	 * @return true/false
	 */
	public boolean parseLogFileIfNew(String filePath) {
		try {
			ParserUsageDAO usageDao = new ParserUsageDAO();
			if (usageDao.getCountByFilePath(filePath) == 0) {
				System.out.println("----Started to parse file and store in db----");
				long startTime = System.currentTimeMillis();
				readAndSaveRecordFromLogFile(filePath);
				long takenTime = System.currentTimeMillis() - startTime;
				System.out.println("Time taken to read and store file : " + takenTime);
				// keep log//
				usageDao.insert(new ParserUsageModel(filePath, "read_success", takenTime));
			} else {
				System.out.println(
						"Provided log file already parsed and stored in db. Going to search data from existing dataabse ");
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
		return false;
	}

	/**
	 * This method will first search in blocked_ip table to check if same request
	 * came or not If in blocked_ip table match not found then will go in
	 * log_records table to fetch blocked ip using provied parameter and will store
	 * them in blocked_ip table as well as return the same list.
	 * 
	 * @param startTime
	 *            = from commandline
	 * @param duration
	 *            = hourly or daily
	 * @param threshold
	 *            = numeric total hit >
	 * @return list<BlockedIpModel>
	 */
	public List<BlockedIpModel> findBlockIpList(String startTime, String duration, int threshold) {
		List<BlockedIpModel> blockIpList = null;
		try {
			Date startDate = Timestamp.valueOf(startTime.replace(".", " "));
			Date endDate = null;
			StringBuilder blockReason = new StringBuilder();
			if (duration.equalsIgnoreCase("hourly")) {
				blockReason.append("More than " + threshold + " request found in a hour within ");
				endDate = new Timestamp(startDate.getTime() + ONE_HOUR);
			} else {
				endDate = new Timestamp(startDate.getTime() + ONE_HOUR * 24);
				blockReason.append("More than " + threshold + " request found in a day within ");
			}
			System.out.println("Start time : " + startTime + " end time " + endDate);
			blockReason.append(startDate + " to " + endDate);
			BlockedIpService bIpService = new BlockedIpService();
			blockIpList = bIpService.getBlockedIpList(startDate, endDate, threshold);
			if (blockIpList.size() == 0) {
				List<LogRecordModel> logList = new LogRecordDAO().getLogIpList(startDate, endDate, threshold);
				if (logList != null && logList.size() > 0) {
					blockIpList = bIpService.buildBlockedIpList(logList, startDate, endDate, threshold,
							blockReason.toString());
					if (blockIpList != null) {
						new BlockedIpDAO().insertBatch(blockIpList);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return blockIpList;
	}
}
