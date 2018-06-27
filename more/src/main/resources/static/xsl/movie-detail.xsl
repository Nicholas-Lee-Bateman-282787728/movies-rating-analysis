<xsl:stylesheet version="1.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:ns="http://anhkhue.io/schema/movies">

    <xsl:output method="html" indent="yes" encoding="utf-8"/>

    <xsl:variable name="lower" select="'abcdefghijklmnopqrstuvwxyz'"/>

    <xsl:variable name="upper" select="'ABCDEFGHIJKLMNOPQRSTUVWXYZ'"/>

    <xsl:template match="/">
        <xsl:apply-templates/>
    </xsl:template>

    <xsl:template match="/ns:movie">
        <input type="hidden" id="movie-id" value="{ns:id}"/>
        <div class="col-md-5 col-xs-12 text-center movie-img">
            <img class="img-thumbnail mb-3" src="{ns:poster}" alt=""/>
            <div id="message">
                Giúp chúng tôi đánh giá phim này
            </div>
            <div id="star-rating" class="star-rating">
                <xsl:call-template name="starRating">
                    <xsl:with-param name="starNumber" select="1"/>
                </xsl:call-template>
                <xsl:call-template name="starRating">
                    <xsl:with-param name="starNumber" select="2"/>
                </xsl:call-template>
                <xsl:call-template name="starRating">
                    <xsl:with-param name="starNumber" select="3"/>
                </xsl:call-template>
                <xsl:call-template name="starRating">
                    <xsl:with-param name="starNumber" select="4"/>
                </xsl:call-template>
                <xsl:call-template name="starRating">
                    <xsl:with-param name="starNumber" select="5"/>
                </xsl:call-template>
            </div>
        </div>

        <div class="card col-md-7 col-xs-12">
            <h1 class="product-title">
                <xsl:value-of select="ns:title"/>
            </h1>
            <div class="row movie-info">
                <div class="col-md-3">Năm:</div>
                <div class="col">
                    <xsl:value-of select="ns:year"/>
                </div>
            </div>
            <div class="row movie-info">
                <div class="col-md-3">Đạo diễn:</div>
                <div class="col">
                    <xsl:value-of select="ns:director"/>
                </div>
            </div>
            <div class="row movie-info">
                <div class="col-md-3">Diễn viên:</div>
                <div class="col">
                    <xsl:apply-templates select="ns:actors"/>...
                </div>
            </div>
            <div class="row movie-info">
                <div class="col-md-3">Thể loại:</div>
                <div class="col">
                    <xsl:apply-templates select="ns:categories"/>
                </div>
            </div>
            <div class="row movie-info">
                <div class="col-md-3">Đánh giá:</div>
                <div id="movie-rating" class="col">
                    <xsl:value-of select="ns:rating"/>
                    <xsl:text> </xsl:text>
                    <span class="fa fa-star"/>
                </div>
            </div>
            <xsl:if test="ns:onCinema[.='true']">
                <h4 class="movie-link">Đặt vé</h4>
                <div class="buttons-group">
                    <xsl:for-each select="./ns:links/ns:link[ns:isCinema='true']">
                        <a href="{./ns:url}">
                            <button class="btn btn-success">
                                <xsl:value-of select="./ns:source"/>
                            </button>
                        </a>
                    </xsl:for-each>
                </div>
            </xsl:if>
            <xsl:if test="ns:onCinema[.='false']">
                <h4 class="movie-link">Xem phim</h4>
                <div class="buttons-group">
                    <xsl:for-each select="./ns:links/ns:link[ns:isCinema='false']">
                        <a href="{./ns:url}">
                            <button class="btn btn-success">
                                <xsl:value-of select="./ns:source"/>
                            </button>
                        </a>
                    </xsl:for-each>
                </div>
            </xsl:if>
            <div class="vote">
                <strong>91%</strong>
                người xem đánh giá cao phim này
                <strong>(87 votes)</strong>
            </div>
        </div>
    </xsl:template>

    <xsl:template match="ns:actors">
        <xsl:for-each select="ns:actor">
            <xsl:value-of select="text()"/>,<xsl:text> </xsl:text>
        </xsl:for-each>
    </xsl:template>

    <xsl:template match="ns:categories">
        <xsl:for-each select="ns:category">
            <xsl:if test="position() = last()">
                <xsl:value-of select="concat(
                translate(substring(ns:categoryName, 1, 1), $lower, $upper),
                substring(ns:categoryName, 2))"/>
            </xsl:if>
            <xsl:if test="position() != last()">
                <xsl:value-of select="concat(
                translate(substring(ns:categoryName, 1, 1), $lower, $upper),
                substring(ns:categoryName, 2))"/>,
                <xsl:text> </xsl:text>
            </xsl:if>
        </xsl:for-each>
    </xsl:template>

    <xsl:template name="starRating">
        <xsl:param name="starNumber"/>
        <span onclick="rate({6 - $starNumber});">
            <xsl:choose>
                <xsl:when test="ns:rating[round(number(.)) >= $starNumber]">
                    <xsl:attribute name="class">star fa fa-star checked</xsl:attribute>
                </xsl:when>
                <xsl:otherwise>
                    <xsl:attribute name="class">star fa fa-star</xsl:attribute>
                </xsl:otherwise>
            </xsl:choose>
        </span>
    </xsl:template>

</xsl:stylesheet>