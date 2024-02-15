<jsp:include page="../layouts/header.jsp"/>
<!DOCTYPE html>
<html>
    <head>
        <title>Top 10 Clientes con más compras</title>
        <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
        <style>
            .container {
                display: flex;
                justify-content: center;
                align-items: center;
                height: 100vh; /* Esto centra verticalmente */
            }

            canvas {
                max-width: 800px;
                max-height: 500px;
            }
        </style>
    </head>
    <body>
        <div class="container" style="text-align: center;">
            <h1 style="margin-bottom: 0px;">Top 10 clientes con más compras</h1>  
                <canvas id="graficoTop10Clientes"></canvas>
        </div>


        <%@ page import="DaoModels.DaoClientes" %>
        <%@ page import="java.util.Map" %>

        <%
            DaoClientes daoClientes = new DaoClientes();
            Map<String, Integer> top10Clientes = daoClientes.obtenerTop10Clientes();

            // Preparar los datos para el gráfico
            StringBuilder nombresClientesArray = new StringBuilder("[");
            StringBuilder numPedidosArray = new StringBuilder("[");
            boolean firstEntry = true;
            for (Map.Entry<String, Integer> entry : top10Clientes.entrySet()) {
                if (!firstEntry) {
                    nombresClientesArray.append(", ");
                    numPedidosArray.append(", ");
                } else {
                    firstEntry = false;
                }
                nombresClientesArray.append("\"").append(entry.getKey()).append("\"");
                numPedidosArray.append(entry.getValue());
            }
            nombresClientesArray.append("]");
            numPedidosArray.append("]");
        %>

        <script>
            var nombresClientes = <%= nombresClientesArray.toString()%>;
            var numPedidos = <%= numPedidosArray.toString()%>;
            var ctx = document.getElementById('graficoTop10Clientes').getContext('2d');

            var chart = new Chart(ctx, {
                type: 'bar',
                data: {
                    labels: nombresClientes,
                    datasets: [{
                            label: 'Número de Pedidos',
                            data: numPedidos,
                            backgroundColor: 'rgba(54, 162, 235, 0.5)',
                            borderColor: 'rgba(54, 162, 235, 1)',
                            borderWidth: 1
                        }]
                },
                options: {
                    scales: {
                        y: {
                            beginAtZero: true
                        }
                    }
                }
            });
        </script>
    </body>
</html>
<jsp:include page="../layouts/footer.jsp" />