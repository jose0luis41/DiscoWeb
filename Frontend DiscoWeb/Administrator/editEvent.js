
function initEditEvent() {  
    //var ROOT = 'https://cali-planner.appspot.com/_ah/api';
    var ROOT_LOCAL = 'http://localhost:8080/_ah/api'; 
  // Loads the OAuth and helloworld APIs asynchronously, and triggers login
  // when they have completed.
    var apisToLoad;

    var callback = function() {
        infoEvents();
    }
    gapi.client.load('echo', 'v1', callback, ROOT_LOCAL);
}




infoEvents = function(){

    var nameStoraged = localStorage.getItem('nameEventJumbotron');
    document.getElementById("editEventJumbotron").innerHTML= 'Evento: '+nameStoraged;

    var idStoraged = localStorage.getItem('EditEventIdEvento');
    gapi.client.echo.echo.findEvento({'idEvento':idStoraged}).execute(
      function(resp) {
         if( !resp.error && resp!==false){
                document.getElementById("nombreEvento").value= resp.nombre;
                document.getElementById("precioEvento").value= resp.precio;
                document.getElementById("entradasEvento").value= resp.maxEntradas;
                document.getElementById("reservasEvento").value= resp.maxReservas;



            }else{
                console.log('Error');
                alert('ERROR');
            }

        });
}


editEventClick = function(){

    var idStoraged = localStorage.getItem('EditEventIdEvento');
    var nombreEvento = document.getElementById("nombreEvento").value;
    var fechaInicio = document.getElementById("fechaInicio").value;
    var fechaFinal = document.getElementById("fechaFin").value;
    var precio = document.getElementById("precioEvento").value;
    var entradas = document.getElementById("entradasEvento").value;
    var reservas = document.getElementById("reservasEvento").value;

    var jwt = localStorage.getItem('tokeAdmin');

    gapi.client.echo.echo.editEvent({'idEvento':idStoraged,'nombre':nombreEvento,'fechaInicio':fechaInicio,'fechaFinal':fechaFinal,
                                        'precio':precio,'maxEntradas':entradas,'maxReservas':reservas, 'jwt':jwt}).execute(
      function(resp) {
         if( !resp.error && resp!==false){
               alert('Evento modificado');
               window.location ='/Administrator/administratorView.html';
            }else if(resp.code === 403){
                alert('Su sesión ha caducado');
                window.location = "/index.html"
            }

        });
}



function indexPage(){
    window.location="/index.html";
}