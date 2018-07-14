<xsl:stylesheet version="1.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:ns="http://anhkhue.io/schema/report">

    <xsl:output method="html" indent="yes" encoding="utf-8"/>

    <xsl:template match="/">
        <xsl:apply-templates/>
    </xsl:template>

    <xsl:template match="ns:vendors">
        <table id="table-vendor" class="table vendors">
            <thead class="thead-light">
                <tr>
                    <th scope="col">No.</th>
                    <th scope="col">Tên khách hàng</th>
                    <th scope="col">Số điện thoại</th>
                    <th scope="col">Email</th>
                </tr>
            </thead>
            <tbody id="vendor-table-body">
                <xsl:for-each select="ns:vendor">
                    <tr>
                        <th scope="row">
                            <xsl:value-of select="position()"/>
                        </th>
                        <td>
                            <xsl:value-of select="ns:name"/>
                        </td>
                        <td>
                            <xsl:value-of select="ns:tel"/>
                        </td>
                        <td>
                            <xsl:value-of select="ns:email"/>
                        </td>
                    </tr>
                </xsl:for-each>
            </tbody>
        </table>

    </xsl:template>

</xsl:stylesheet>