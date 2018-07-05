<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:ns="http://anhkhue.io/schema/report">

    <xsl:output method="xml" encoding="UTF-8"/>
    <xsl:param name="pathFile" select="''"/>

    <xsl:template match="/">
        <fo:root xmlns:fo="http://www.w3.org/1999/XSL/Format">
            <fo:layout-master-set>
                <fo:simple-page-master master-name="A4-portrait" page-height="8.5in"
                                       page-width="11in" margin-top="0.5in" margin-bottom="0.5in"
                                       margin-left="1in" margin-right="1in">
                    <fo:region-body margin-top="0.5in"/>
                    <fo:region-before extent="1in"/>
                    <fo:region-after extent=".75in"/>
                </fo:simple-page-master>
            </fo:layout-master-set>

            <fo:page-sequence master-reference="A4-portrait">
                <fo:static-content flow-name="xsl-region-before">
                    <fo:block font-size="14pt" font-family="Arial"
                              line-height="24pt"
                              space-after.optimum="15pt" text-align="left"
                              padding-top="20pt">
                        <fo:external-graphic content-height="scale-to-fit"
                                             height="1.10in"
                                             content-width="1.10in"
                                             scaling="non-uniform">
                            <xsl:attribute name="src">
                                <xsl:value-of select="concat($pathFile, //ns:vendor/ns:image)"/>
                            </xsl:attribute>
                        </fo:external-graphic>
                    </fo:block>
                    <fo:block text-align="center"
                              padding-top="0pt">
                        <fo:external-graphic content-height="scale-to-fit"
                                             height="0.50in"
                                             content-width="0.50in"
                                             scaling="non-uniform">
                            <xsl:attribute name="src">
                                <xsl:value-of select="concat($pathFile, //ns:logo)"/>
                            </xsl:attribute>
                        </fo:external-graphic>
                    </fo:block>
                    <fo:block font-size="22pt" font-family="Arial"
                              font-weight="bold"
                              line-height="24pt"
                              space-after.optimum="15pt" text-align="center"
                              padding-top="20pt">
                        Dự báo xếp hạng phim tháng
                        <xsl:value-of select="//ns:date"/>
                    </fo:block>
                </fo:static-content>

                <fo:flow flow-name="xsl-region-body">
                    <fo:block font-size="12pt" font-family="Arial"
                              padding-top="100pt">
                        <fo:block>
                            Khách hàng:
                            <fo:inline font-weight="bold">
                                <xsl:value-of select="//ns:vendor/ns:name"/>
                            </fo:inline>
                        </fo:block>
                        <fo:block>
                            Địa chỉ:
                            <fo:inline font-weight="bold">
                                <xsl:value-of select="//ns:vendor/ns:address1"/>
                            </fo:inline>
                        </fo:block>
                        <fo:block>
                            <fo:inline font-weight="bold">
                                <xsl:value-of select="//ns:vendor/ns:address2"/>
                            </fo:inline>
                        </fo:block>
                        <fo:block>
                            Số điện thoại:
                            <fo:inline font-weight="bold">
                                <xsl:value-of select="//ns:vendor/ns:tel"/>
                            </fo:inline>
                        </fo:block>
                        <fo:block>
                            Email:
                            <fo:inline font-weight="bold">
                                <xsl:value-of select="//ns:vendor/ns:email"/>
                            </fo:inline>
                        </fo:block>
                    </fo:block>

                    <fo:block font-size="12pt" font-family="Arial"
                              padding-top="20pt">
                        <fo:table border-collapse="separate" table-layout="fixed">
                            <fo:table-column column-width="proportional-column-width(1)"/>
                            <fo:table-column column-width="3cm"/>
                            <fo:table-column column-width="7cm"/>
                            <fo:table-column column-width="4cm"/>
                            <fo:table-column column-width="6cm"/>
                            <fo:table-column column-width="proportional-column-width(1)"/>

                            <fo:table-body>
                                <fo:table-row>
                                    <fo:table-cell column-number="2"
                                                   border-color="blue"
                                                   border-width="0.5pt"
                                                   border-style="solid">
                                        <fo:block text-align="center">Xếp hạng</fo:block>
                                    </fo:table-cell>
                                    <fo:table-cell column-number="3"
                                                   border-color="blue"
                                                   border-width="0.5pt"
                                                   border-style="solid">
                                        <fo:block text-align="center">Tựa phim</fo:block>
                                    </fo:table-cell>
                                    <fo:table-cell column-number="4"
                                                   border-color="blue"
                                                   border-width="0.5pt"
                                                   border-style="solid">
                                        <fo:block text-align="center">Năm khởi chiếu</fo:block>
                                    </fo:table-cell>
                                    <fo:table-cell column-number="5"
                                                   border-color="blue"
                                                   border-width="0.5pt"
                                                   border-style="solid">
                                        <fo:block text-align="center">Đạo diễn</fo:block>
                                    </fo:table-cell>
                                </fo:table-row>

                                <xsl:for-each select="//ns:movies/ns:movie">
                                    <fo:table-row>
                                        <fo:table-cell column-number="2"
                                                       border-color="blue"
                                                       border-width="0.5pt"
                                                       border-style="solid"
                                                       display-align="center">
                                            <fo:block text-align="center">
                                                <xsl:number level="single" count="ns:movie"/>
                                            </fo:block>
                                        </fo:table-cell>
                                        <fo:table-cell column-number="3"
                                                       border-color="blue"
                                                       border-width="0.5pt"
                                                       border-style="solid"
                                                       display-align="center">
                                            <fo:block text-align="left" margin-left="2mm">
                                                <xsl:value-of select="ns:title"/>
                                            </fo:block>
                                        </fo:table-cell>
                                        <fo:table-cell column-number="4"
                                                       border-color="blue"
                                                       border-width="0.5pt"
                                                       border-style="solid"
                                                       display-align="center">
                                            <fo:block text-align="center">
                                                <xsl:value-of select="ns:year"/>
                                            </fo:block>
                                        </fo:table-cell>
                                        <fo:table-cell column-number="5"
                                                       border-color="blue"
                                                       border-width="0.5pt"
                                                       border-style="solid"
                                                       display-align="center">
                                            <fo:block text-align="left" margin-left="2mm">
                                                <xsl:value-of select="ns:director"/>
                                            </fo:block>
                                        </fo:table-cell>
                                    </fo:table-row>
                                </xsl:for-each>
                            </fo:table-body>
                        </fo:table>
                    </fo:block>
                </fo:flow>
            </fo:page-sequence>
        </fo:root>
    </xsl:template>

</xsl:stylesheet>