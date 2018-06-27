package io.anhkhue.more.crawlservice.crawlers;

import java.io.Serializable;

public class CrawlerConstants implements Serializable {

    public static final class PageTypeConstants {

        public static final int NEW_LIST = 0;
        public static final int PAGINATION_LIST = 1;
        public static final int COMING_LIST = 2;
        public static final int NEW_DETAIL = 3;
        public static final int COMING_DETAIL = 4;
    }

    public static class CategoryConstants {

        public static final String[] normalizedArray = new String[]{
                "chien tranh", "gia dinh", "hai", "hanh dong",
                "hinh su", "hoat hinh", "hoc duong", "kiem hiep",
                "kinh di", "lang man", "phieu luu", "tam ly",
                "than thoai", "the thao - am nhac", "tinh cam",
                "toi pham", "vien tuong", "vo thuat", "trinh tham"
        };

        public static final String[] standardizedArray = new String[]{
                "chiến tranh", "gia đình", "hài", "hành động",
                "hình sự", "hoạt hình", "học đường", "kiếm hiệp",
                "kinh dị", "lãng mạn", "phiêu lưu", "tâm lý",
                "thần thoại", "thể thao - âm nhạc", "tình cảm",
                "tội phạm", "viễn tưởng", "võ thuật", "trinh thám"
        };
    }
}
