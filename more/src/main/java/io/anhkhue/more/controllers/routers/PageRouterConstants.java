package io.anhkhue.more.controllers.routers;

import java.util.HashMap;
import java.util.Map;

class PageRouterConstants {

    static final Map<String, String> ROUTER = new HashMap<String, String>() {{
        put(Url.INDEX, Page.TRANG_CHU);
        put(Url.TRANG_CHU, Page.TRANG_CHU);
        put(Url.PHIM_MOI, Page.PHIM_MOI);
        put(Url.SAP_RA_MAT, Page.SAP_RA_MAT);
        put(Url.CHI_TIET, Page.CHI_TIET);
        put(Url.DANG_NHAP, Page.DANG_NHAP);
        put(Url.DANG_KY, Page.DANG_KY);
    }};

    static class Url {

        static final String INDEX = "/";
        static final String TRANG_CHU = "trang-chu";
        static final String PHIM_MOI = "phim-moi";
        static final String SAP_RA_MAT = "sap-ra-mat";
        static final String CHI_TIET = "phim";
        static final String DANG_NHAP = "dang-nhap";
        static final String DANG_KY = "dang-ky";
    }

    private static class Page {

        private static final String TRANG_CHU = "trang-chu";
        private static final String PHIM_MOI = "phim-moi";
        private static final String SAP_RA_MAT = "sap-ra-mat";
        private static final String CHI_TIET = "chi-tiet";
        private static final String DANG_NHAP = "dang-nhap";
        private static final String DANG_KY = "dang-ky";
    }
}
