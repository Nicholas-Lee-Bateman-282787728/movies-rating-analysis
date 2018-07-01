package io.anhkhue.more.controllers.routers;

import java.util.HashMap;
import java.util.Map;

interface PageRouterConstants {

    Map<String, String> ROUTER = new HashMap<String, String>() {{
        put(Url.INDEX, Page.TRANG_CHU);
        put(Url.TRANG_CHU, Page.TRANG_CHU);
        put(Url.PHIM_MOI, Page.PHIM_MOI);
        put(Url.SAP_RA_MAT, Page.SAP_RA_MAT);
        put(Url.CHI_TIET, Page.CHI_TIET);
        put(Url.DANG_NHAP, Page.DANG_NHAP);
        put(Url.DANG_XUAT, Page.DANG_XUAT);
        put(Url.DANG_KY, Page.DANG_KY);

        put(Url.CRAWLER_SWITCH, Page.CRAWLER_SWITCH);
    }};

    interface Url {

        String INDEX = "/";
        String TRANG_CHU = "trang-chu";
        String PHIM_MOI = "phim-moi";
        String SAP_RA_MAT = "sap-ra-mat";
        String CHI_TIET = "phim";
        String DANG_NHAP = "dang-nhap";
        String DANG_XUAT = "dang-xuat";
        String DANG_KY = "dang-ky";

        String CRAWLER_SWITCH = "admin/crawler-switch";
    }

    interface Page {

        String TRANG_CHU = "trang-chu";
        String PHIM_MOI = "phim-moi";
        String SAP_RA_MAT = "sap-ra-mat";
        String CHI_TIET = "chi-tiet";
        String DANG_NHAP = "dang-nhap";
        String DANG_XUAT = "trang-chu";
        String DANG_KY = "dang-ky";

        String CRAWLER_SWITCH = "admin/crawler-switch";
    }
}
