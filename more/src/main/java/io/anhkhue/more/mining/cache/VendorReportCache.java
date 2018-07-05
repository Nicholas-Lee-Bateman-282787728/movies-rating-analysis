package io.anhkhue.more.mining.cache;

import io.anhkhue.more.models.mining.report.Report;

import java.util.HashMap;
import java.util.Map;

public class VendorReportCache {

    private static Map<Integer, Map<String, Report>> reportCachedMap = new HashMap<>();

    public static void cache(Integer vendorId, String reportKey, Report report) {
        if (!reportCachedMap.containsKey(vendorId)) {
            Map<String, Report> reportMap = new HashMap<String, Report>() {{
                put(reportKey, report);
            }};
            reportCachedMap.put(vendorId, reportMap);
        } else {
            if (!reportCachedMap.get(vendorId).containsKey(reportKey)) {
                reportCachedMap.get(vendorId).put(reportKey, report);
            }
        }
    }

    public static Map<Integer, Map<String, Report>> getReportCachedMap() {
        return reportCachedMap;
    }
}
