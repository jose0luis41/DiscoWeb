
function initAddEvent() {  
    //var ROOT = 'https://cali-planner.appspot.com/_ah/api';
    var ROOT_LOCAL = 'http://localhost:8080/_ah/api'; 
  // Loads the OAuth and helloworld APIs asynchronously, and triggers login
  // when they have completed.
    var apisToLoad;

    var callback = function() {
    	
    }
    gapi.client.load('echo', 'v1', callback, ROOT_LOCAL);
}




addEventClick = function(){


    var nombreEvento = document.getElementById("nombreEvento").value;
    var fechaInicio = document.getElementById("fechaInicio").value;
    var fechaFinal = document.getElementById("fechaFin").value;
    var precio = document.getElementById("precioEvento").value;
    var entradas = document.getElementById("entradasEvento").value;
    var reservas = document.getElementById("reservasEvento").value;
    var idDiscoteca =  localStorage.getItem('idDiscoActual');
    var jwt = localStorage.getItem('tokeAdmin');

    gapi.client.echo.echo.createEvent({'nombre':nombreEvento,'fechaInicio':fechaInicio,'fechaFinal':fechaFinal,
      						'precioEvento':precio,'maxEntradas':entradas,'maxReservas':reservas, 'idDisco':idDiscoteca, 'jwt':jwt}).execute(
      function(resp) {
         if( !resp.error && resp!==false){
               alert('Evento agregado');
               window.location ='/Administrator/administratorView.html';
            }else if(resp.code === 403){
                alert('Su sesión ha caducado');
                window.location = "/index.html"
            }

        });
}



