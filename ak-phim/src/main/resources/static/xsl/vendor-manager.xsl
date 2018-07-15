<xsl:stylesheet version="1.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:ns="http://anhkhue.io/schema/report">

    <xsl:output method="html" indent="yes" encoding="utf-8"/>

    <xsl:template match="/">
        <xsl:apply-templates/>
    </xsl:template>

    <xsl:template match="ns:vendors">

        <div class="table">
            <div class="table-header">
                <div class="table-header-item">
                    No.
                </div>
                <div class="table-header-item">
                    Tên khách hàng
                </div>
                <div class="table-header-item">
                    Số điện thoại
                </div>
                <div class="table-header-item">
                    Email
                </div>
            </div>
            <div id="table-vendor" class="table-body">
                <xsl:for-each select="ns:vendor">
                    <div class="table-body-row">
                        <div class="table-body-item">
                            <xsl:value-of select="position()"/>
                        </div>
                        <div class="table-body-item">
                            <xsl:value-of select="ns:name"/>
                        </div>
                        <div class="table-body-item">
                            <xsl:value-of select="ns:tel"/>
                        </div>
                        <div class="table-body-item">
                            <xsl:value-of select="ns:email"/>
                        </div>
                    </div>
                </xsl:for-each>
            </div>
        </div>

    </xsl:template>

</xsl:stylesheet>