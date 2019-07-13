package com.ef;

/**
 * This Java Program developer by Palash Kuamr Nath
 * Mail : palash.debnath5@gmail.com
 * 
 */
import java.util.HashMap;
import java.util.List;

import com.ef.model.BlockedIpModel;
import com.ef.service.ParserService;
import com.ef.util.ApplicationConstants;
import com.ef.util.ParserUtil;

public class Parser {

	public static void main(String[] args) {
		if (args.length < 1) {
			System.exit(0);
		}
		HashMap<String, String> arguments = Parser.processArguments(args);
		if (arguments.size() > 0) {
			startParsing(arguments);
		}
	}

	private static HashMap<String, String> processArguments(String... args) {
		HashMap<String, String> argumentMap = new HashMap<>(5);
		String[] temp = null;
		for (String arg : args) {
			try {
				temp = arg.split("=");
				argumentMap.put(temp[0].replaceAll("--", ""), temp[1]);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return argumentMap;
	}

	/**
	 * First check if log file already parsed and stored or not if stored then go
	 * for database search direct If not stored then first parse and store all log
	 * and then go for search data from database
	 */
	private static void startParsing(HashMap<String, String> arguments) {
		ParserService ps = new ParserService();
		if (arguments.containsKey(ApplicationConstants.PARAM_START_DATE)
				&& arguments.containsKey(ApplicationConstants.PARAM_DURATION)
				&& arguments.containsKey(ApplicationConstants.PARAM_THRESHOLD)) {
			boolean fileParsed = true;
			if (arguments.containsKey(ApplicationConstants.PARAM_ACCESS_LOG)) {
				fileParsed = ps.parseLogFileIfNew(arguments.get(ApplicationConstants.PARAM_ACCESS_LOG));
			}
			if (fileParsed) {
				String startDate = arguments.get(ApplicationConstants.PARAM_START_DATE);
				String duration = arguments.get(ApplicationConstants.PARAM_DURATION);
				int threshold = ParserUtil.parseInt(arguments.get(ApplicationConstants.PARAM_THRESHOLD));
				List<BlockedIpModel> blockIpList = ps.findBlockIpList(startDate, duration, threshold);
				if (blockIpList != null && blockIpList.size() > 0) {
					blockIpList.forEach(
							ip -> System.out.println("IP:" + ip.getIp() + ", Threshold : " + ip.getThreshold()));
				} else {
					System.out.println("No IP found that sent request more than " + threshold
							+ " times within preovided time range");
				}
			} else {
				System.out.println("Something went wrong to read file");
			}
		} else {
			System.out.println("Required parameter : " + ApplicationConstants.PARAM_START_DATE + ","
					+ ApplicationConstants.PARAM_DURATION + "," + ApplicationConstants.PARAM_THRESHOLD
					+ " was missing");
		}
		System.exit(0);
	}
}
