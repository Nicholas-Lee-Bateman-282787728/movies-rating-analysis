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
}
