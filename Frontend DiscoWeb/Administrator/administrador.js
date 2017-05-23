
function initWelcomeAdmin() {  
    //var ROOT = 'https://cali-planner.appspot.com/_ah/api';
    var ROOT_LOCAL = 'http://localhost:8080/_ah/api'; 
  // Loads the OAuth and helloworld APIs asynchronously, and triggers login
  // when they have completed.
    var apisToLoad;

    var callback = function() {
    	getAdminLogin();
        listarEventosDisco();
    }
    gapi.client.load('echo', 'v1', callback, ROOT_LOCAL);
}


getAdminLogin = function(){
   var emailAdminStoraged = localStorage.getItem('administrador'); 

	 gapi.client.echo.echo.findAdministratorByCorreo({'correo':emailAdminStoraged}).execute(
      function(resp) {
            if( !resp.error && resp!==false){
                localStorage.setItem('idDiscoActual',resp.discotecaidDiscoteca.idDiscoteca);
            	document.getElementById("welcome").innerHTML = "Bienvenido a "+ resp.discotecaidDiscoteca.nombre;	
            	document.getElementById("userNameAdmin").innerHTML = resp.nombre;	

            	document.title = resp.nombre;
            	return resp;
            }else if(resp.code ===400){
                    alert(resp.error);
            }else{
                alert("Error "+ resp.error);
            } 
    });

}



listarEventosDisco = function() {

   var idDicosStoraged = localStorage.getItem('idDiscoActual'); 
  gapi.client.echo.echo.getEventsByAdministrators({'idDiscoteca':idDicosStoraged}).execute(
      function(resp) {
        
    var table = document.getElementById("EventsByAdmin");
    var rowCount = table.rows.length;


            var row = table.insertRow(rowCount);

            var events = resp;
            for(var i = 0; i < events.items.length; i++)
            {

             var cellId= row.insertCell(0); 
             var cellNombre= row.insertCell(1); 
             var cellFecha= row.insertCell(2); 
             var cellFechaFin= row.insertCell(3); 
             var cellPrecio= row.insertCell(4); 
             var celCantidadEntradas = row.insertCell(5);
             var celCantidadReservas = row.insertCell(6);
             var cellEdit = row.insertCell(7); 
             var cellDelete = row.insertCell(8);

                cellId.innerHTML = events.items[i].idEvento;
                cellNombre.innerHTML = events.items[i].nombre;
                cellFecha.innerHTML = events.items[i].fechaInicio;
                cellFechaFin.innerHTML = events.items[i].fechaFinal;
                cellPrecio.innerHTML = '<p> $'+events.items[i].precio+'</p>';
                celCantidadEntradas.innerHTML = events.items[i].maxEntradas;
                celCantidadReservas.innerHTML = events.items[i].maxReservas;
                cellEdit.innerHTML =   '<button type="button" onclick="edtiEventPage()" class="btn btn-default btn-sm"><span class="glyphicon glyphicon-pencil"></span> Edit </button>';  
                 cellDelete.innerHTML =   '<button type="button" onclick="deleteEventClick()" class="btn btn-default btn-sm"><span class="glyphicon glyphicon-trash"></span> Delete </button>';  


                row = table.insertRow(table.rows.length);

            }
         

});
}


var table = document.getElementsByTagName("table")[0];
table.onclick = function getIdEventSelected(e) {
    var data = [];
    e = e || window.event;
    var target = e.srcElement || e.target;
    while (target && target.nodeName !== "TR") {
        target = target.parentNode;
    }
    if (target) {
        var cells = target.getElementsByTagName("td");
        for (var i = 0; i < cells.length; i++) {
            data.push(cells[i].innerHTML);
        }
    }
    localStorage.setItem('nameEventJumbotron',data[1]);
    localStorage.setItem('EditEventIdEvento',data[0]);
};


deleteEventClick = function(){
    var idStoraged = localStorage.getItem('EditEventIdEvento');
    var jwt = localStorage.getItem('tokeAdmin');

       gapi.client.echo.echo.deleteEvent({'idEvento':idStoraged,'jwt':jwt}).execute(
      function(resp) {
         if( !resp.error && resp!==false){
               alert('Evento eliminado');
               window.location ='/Administrator/administratorView.html';
            }else if(resp.code === 403){
                alert('Su sesión ha caducado');
                window.location = "/index.html"
            }

        });
     
}






function addEventPage(){
  window.location = '/Administrator/addEvent.html';
}



function edtiEventPage(){
    window.location = '/Administrator/editEvent.html';
}



