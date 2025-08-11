<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:variable name="page_stereotype" select="page/header/@stereotype" />
    <xsl:variable name="url_prefix" select="page/header/layouts/@url_prefix" />

    <xsl:strip-space elements="*" />

    <xsl:template match="/">
        <xsl:apply-templates select="*" />
    </xsl:template>

    <xsl:template match="page/header" />
    <xsl:template match="action" />
    <xsl:template match="panel[starts-with(@name,'filter_pane')]" />
    <xsl:template match="div_responsive[@name='filter_header']" />
    <xsl:template match="div_responsive[@name='filter_body ']" />
    <xsl:template match="form_responsive[starts-with(@name,'filter_pane')]" />
    <xsl:template match="*/text_field_responsive" />

    <xsl:template match="win_list">
        <HTML>
        <HEAD>
            <TITLE><xsl:value-of select="@title"/></TITLE>
            <link rel="stylesheet">
                <xsl:attribute name="href">/<xsl:value-of select="$url_prefix"/>/resources/styles[<xsl:value-of select="session/@id"/>].css</xsl:attribute>
            </link>

            <!-- Importar Chart.js -->
            <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>

 <SCRIPT LANGUAGE="JavaScript">
    var graphs = [];

    <xsl:for-each select="graph/graphic">
				var chartType = '<xsl:value-of select="@swf"/>'; // Extraer el tipo de gráfico
				if (chartType === 'area') chartType = 'line'; // Area es una línea con relleno
				if (chartType === 'mixed') chartType = 'bar'; // Mixed usa barras y líneas combinadas
				if (chartType === 'pie') chartType = 'bar'; // Si es pie, se mantiene pie

        graphs.push({
            title: '<xsl:value-of select="@title"/>',
            type: chartType, // Tipo de gráfico dinámico
            labels: [
                <xsl:for-each select="categories">
                    '<xsl:value-of select="@name"/>',
                </xsl:for-each>
            ],
            datasets: [
                <xsl:for-each select="dataset">
                {
                    type: chartType,
                    label: '<xsl:value-of select="@name"/>',
                    backgroundColor: [
                        <xsl:for-each select="value">
                            "#<xsl:value-of select="../@color"/>",
                        </xsl:for-each>
                    ],
                    borderColor: "'#<xsl:value-of select="@color"/>',",
                    fill: (chartType === 'area') ? true : false, // Area debe tener relleno
                    data: [
                        <xsl:for-each select="value">
                            <xsl:value-of select="@value"/>,
                        </xsl:for-each>
                    ]
                },
                </xsl:for-each>
            ]
        });
    </xsl:for-each>

    function renderChart(graphIndex) {
        var ctx = document.getElementById("chartCanvas").getContext("2d");
        if (window.myChart) {
            window.myChart.destroy();
        }
        window.myChart = new Chart(ctx, {
            type: graphs[graphIndex].type, // Usa el tipo dinámico
            data: {
                labels: graphs[graphIndex].labels,
                datasets: graphs[graphIndex].datasets
            },
            options: {
                responsive: true,
                scales: {
                    y: {
                        beginAtZero: true
                    }
                }
            }
        });
    }
</SCRIPT>

        </HEAD>

        <BODY onload="renderChart(0)">
            <center>
                <table border="0" ALIGN="CENTER" class="win_form_border" WIDTH="740">
                    <tr height="480" VALIGN="MIDDLE">
                        <td ALIGN="CENTER">
                            <canvas id="chartCanvas" width="702" height="450"></canvas>
                        </td>
                    </tr>

                </table>
            </center>
        </BODY>
        </HTML>
    </xsl:template>

</xsl:stylesheet>
