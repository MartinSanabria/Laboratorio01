<jsp:include page="../layouts/header.jsp"/>
<!DOCTYPE html>
<html>
<head>
    <title>Top 3 Meses con más pedidos por Año</title>
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
    <style>
        .grafico-pair-container {
            display: flex;
            justify-content: center;
            align-items: center;
            margin-bottom: 20px;
        }

        .grafico-container {
            display: inline-block;
            margin: 0 10px;
            text-align: center;
        }

        .titulo-grafico {
            font-size: 18px;
            margin-bottom: 10px;
        }

        .titulo-central {
            font-size: 24px;
            text-align: center;
            margin-bottom: 20px;
        }

        canvas {
            width: 400px;
            height: 200px;
        }
    </style>
</head>
<body>
            <br><br>
    <div class="titulo-central">Top 3 Meses con más pedidos por año</div>
    <div>
    <%@ page import="DaoModels.DaoPedidos" %>
    <%@ page import="java.util.Map" %>
    <%@ page import="java.util.List" %>
    <%@ page import="java.util.ArrayList" %>

    <%
        DaoPedidos daoPedidos = new DaoPedidos();
        Map<Integer, List<Integer>> top3MesesPorAnio = daoPedidos.obtenerTop3MesesPorAnio();
    %>

    <%
        int contador = 0;
        for (Map.Entry<Integer, List<Integer>> entry : top3MesesPorAnio.entrySet()) {
            int anio = entry.getKey();
            if(anio == 0) continue; // Saltar el año 0
            List<Integer> top3Meses = entry.getValue();
    %>

    <% if (contador % 2 == 0) { %>
    <div class="grafico-pair-container">
    <% } %>

    <div class="grafico-container">
        <div class="titulo-grafico"><%=anio%></div>
        <canvas id="graficoPedidos_<%=anio%>"></canvas>
    </div>

    <% if (contador % 2 != 0 || contador == top3MesesPorAnio.size() - 1) { %>
    </div>
    <% } %>

    <script>
        var top3Meses = <%= top3Meses %>;
        var canvasId = "graficoPedidos_<%=anio%>";
        var canvas = document.getElementById(canvasId);
        var ctx = canvas.getContext("2d");

        var data = {
            labels: [],
            datasets: [{
                label: "cantidad de pedidos",
                data: [],
                backgroundColor: [
                    "rgba(255, 99, 132, 0.2)",
                    "rgba(54, 162, 235, 0.2)",
                    "rgba(255, 206, 86, 0.2)"
                ],
                borderColor: [
                    "rgba(255, 99, 132, 1)",
                    "rgba(54, 162, 235, 1)",
                    "rgba(255, 206, 86, 1)"
                ],
                borderWidth: 1
            }]
        };

        // Mapeo de nombres de los meses
        var meses = ["Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"];

        // Llenar 'data.labels' con los nombres de los meses y 'data.datasets[0].data' con el número de pedidos para cada mes.
        <% for (Integer mes : top3Meses) { %>
            var nombreMes = meses[<%= mes - 1 %>]; // Se resta 1 porque los índices de los meses comienzan en 0
            data.labels.push(nombreMes);
            data.datasets[0].data.push(<%= daoPedidos.obtenerTotalPedidosPorMesYAnio(mes, anio) %>);
        <% } %>

        var chart = new Chart(ctx, {
            type: "bar",
            data: data,
            options: {
                scales: {
                    y: {
                        beginAtZero: true
                    }
                }
            }
        });
    </script>

    <% contador++;
        }
    %>
    </div>
</body>
</html>
<jsp:include page="../layouts/footer.jsp" />