
package io.anhkhue.more.crawlservice.xml.jaxb.mappings;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "Category")
@XmlEnum
public enum Category {

    @XmlEnumValue("chi\u1ebfn tranh")
    CHIẾN_TRANH("chi\u1ebfn tranh"),
    @XmlEnumValue("gia \u0111\u00ecnh")
    GIA_ĐÌNH("gia \u0111\u00ecnh"),
    @XmlEnumValue("h\u00e0i")
    HÀI("h\u00e0i"),
    @XmlEnumValue("h\u00e0nh \u0111\u1ed9ng")
    HÀNH_ĐỘNG("h\u00e0nh \u0111\u1ed9ng"),
    @XmlEnumValue("h\u00ecnh s\u1ef1")
    HÌNH_SỰ("h\u00ecnh s\u1ef1"),
    @XmlEnumValue("ho\u1ea1t h\u00ecnh")
    HOẠT_HÌNH("ho\u1ea1t h\u00ecnh"),
    @XmlEnumValue("h\u1ecdc \u0111\u01b0\u1eddng")
    HỌC_ĐƯỜNG("h\u1ecdc \u0111\u01b0\u1eddng"),
    @XmlEnumValue("ki\u1ebfm hi\u1ec7p")
    KIẾM_HIỆP("ki\u1ebfm hi\u1ec7p"),
    @XmlEnumValue("kinh d\u1ecb")
    KINH_DỊ("kinh d\u1ecb"),
    @XmlEnumValue("l\u00e3ng m\u1ea1n")
    LÃNG_MẠN("l\u00e3ng m\u1ea1n"),
    @XmlEnumValue("phi\u00eau l\u01b0u")
    PHIÊU_LƯU("phi\u00eau l\u01b0u"),
    @XmlEnumValue("t\u00e2m l\u00fd")
    TÂM_LÝ("t\u00e2m l\u00fd"),
    @XmlEnumValue("th\u1ea7n tho\u1ea1i")
    THẦN_THOẠI("th\u1ea7n tho\u1ea1i"),
    @XmlEnumValue("th\u1ec3 thao - \u00e2m nh\u1ea1c")
    THỂ_THAO_ÂM_NHẠC("th\u1ec3 thao - \u00e2m nh\u1ea1c"),
    @XmlEnumValue("t\u00ecnh c\u1ea3m")
    TÌNH_CẢM("t\u00ecnh c\u1ea3m"),
    @XmlEnumValue("t\u1ed9i ph\u1ea1m")
    TỘI_PHẠM("t\u1ed9i ph\u1ea1m"),
    @XmlEnumValue("trinh th\u00e1m")
    TRINH_THÁM("trinh th\u00e1m"),
    @XmlEnumValue("vi\u1ec5n t\u01b0\u1edfng")
    VIỄN_TƯỞNG("vi\u1ec5n t\u01b0\u1edfng"),
    @XmlEnumValue("v\u00f5 thu\u1eadt")
    VÕ_THUẬT("v\u00f5 thu\u1eadt");
    private final String value;

    Category(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static Category fromValue(String v) {
        for (Category c: Category.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}