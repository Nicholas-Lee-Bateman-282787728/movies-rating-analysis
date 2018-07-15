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

        <section id="main-body">
            <div id="main-body-wrapper">

                <div id="left_col">

                    <div class="item-box">
                        <img src="{ns:poster}"/>
                    </div>

                    <xsl:if test="ns:isComing[.='true']">
                        <div class="alert alert-info">
                            Phim sắp ra mắt
                        </div>
                    </xsl:if>

                    <xsl:if test="ns:isComing[.='false']">
                        <div id="message" class="help">
                            Giúp chúng tôi đánh giá phim này
                        </div>
                        <div class="star-rating">
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
                    </xsl:if>

                </div>

                <div id="right_col">
                    <aside id="main-info">
                        <h1 class="title">
                            <xsl:value-of select="ns:title"/>
                        </h1>
                        <h2 class="year">
                            <xsl:value-of select="ns:year"/>
                        </h2>
                    </aside>
                    <aside class="category">
                        <h1>Thể loại</h1>
                        <xsl:apply-templates select="ns:categories"/>
                    </aside>
                    <aside class="director">
                        <h1>Đạo diễn</h1>
                        <xsl:value-of select="ns:director"/>
                    </aside>
                    <aside class="actor">
                        <h1>Diễn viên</h1>
                        <xsl:apply-templates select="ns:actors"/>
                    </aside>
                    <aside id="movie-rating" class="rate-no">
                        <h1>Đánh giá</h1>
                        <xsl:value-of select="format-number(number(ns:rating), '0.##')"/>
                        <xsl:text> </xsl:text>
                        <span class="fa fa-star"/>
                    </aside>
                    <div id="watch-buttons">

                        <xsl:if test="ns:onCinema[.='true']">
                            <h2>Đặt vé</h2>
                            <div class="buttons-group">
                                <xsl:for-each select="./ns:links/ns:link[ns:isCinema='true']">
                                    <a href="{./ns:url}">
                                        <button>
                                            <xsl:value-of select="./ns:source"/>
                                        </button>
                                    </a>
                                </xsl:for-each>
                            </div>
                        </xsl:if>
                        <xsl:if test="ns:onCinema[.='false']">
                            <h2>Xem phim</h2>
                            <div class="buttons-group">
                                <xsl:for-each select="./ns:links/ns:link[ns:isCinema='false']">
                                    <a href="{./ns:url}">
                                        <button>
                                            <xsl:value-of select="./ns:source"/>
                                        </button>
                                    </a>
                                </xsl:for-each>
                            </div>
                        </xsl:if>

                    </div>
                    <aside id="high-votes">

                    </aside>
                </div>
            </div>

            <div class="main-panel">
                <div class="panel-header">
                    <div class="panel-title">
                        Có thể bạn sẽ thích
                    </div>
                </div>
                <div id="similar">

                </div>
            </div>

        </section>

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