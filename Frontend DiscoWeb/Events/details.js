 function initEvent() {  
    //var ROOT = 'https://disco-web.appspot.com/_ah/api';
    var ROOT_LOCAL = 'http://localhost:8080/_ah/api'; 
  // Loads the OAuth and helloworld APIs asynchronously, and triggers login
  // when they have completed.
    var apisToLoad;

    var callback = function() {
        getEventLogin();
        getEvent();

    }
    gapi.client.load('echo', 'v1', callback, ROOT_LOCAL);

    //gapi.client.load('echo', 'v1', callback, ROOT);
}



getEventLogin = function(){
   var eventStoraged = localStorage.getItem('idEvento'); 

	 gapi.client.echo.echo.findEvento({'idEvento':eventStoraged}).execute(
      function(resp) {

            if( !resp.error && resp!==false){
              var eventPrecio = localStorage.setItem('precioEvento',resp.precio); 

            	document.getElementById("eventName").innerHTML = "Evento: "+ resp.nombre;	

            	document.title = resp.nombre;
            	return resp;
            }else if(resp.code ===400){
                    alert(resp.error);
            }else{
                alert("Error "+ resp.error);
            } 
    });

}



getEvent = function() {
    var idEventoStoraged = localStorage.getItem('idEvento');
  
  gapi.client.echo.echo.findEvento({'idEvento':idEventoStoraged}).execute(
      function(resp) {
        
    var table = document.getElementById("EventoDetallado");
    var rowCount = table.rows.length;


            var row = table.insertRow(rowCount);

            var evento = resp;
            
             var cellId= row.insertCell(0); 
             var cellNombre= row.insertCell(1); 
             var cellFecha= row.insertCell(2); 
             var cellFechaFin= row.insertCell(3); 
             var cellPrecio= row.insertCell(4); 
             var celCantidadEntradas = row.insertCell(5);
             var celCantidadReservas = row.insertCell(6);


                cellId.innerHTML = evento.idEvento;
                cellNombre.innerHTML = evento.nombre;
                cellFecha.innerHTML = evento.fechaInicio;
                cellFechaFin.innerHTML = evento.fechaFinal;
                cellPrecio.innerHTML = evento.precio;
                celCantidadEntradas.innerHTML = evento.maxEntradas;
                celCantidadReservas.innerHTML = evento.maxReservas;


                row = table.insertRow(table.rows.length);

            
         

});
}