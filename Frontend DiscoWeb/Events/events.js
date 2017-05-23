 function init() {  
    var ROOT = 'https://disco-web.appspot.com/_ah/api';
    //var ROOT_LOCAL = 'http://localhost:8080/_ah/api'; 
  // Loads the OAuth and helloworld APIs asynchronously, and triggers login
  // when they have completed.
    var apisToLoad;

    var callback = function() {
        listarEventos();

    }
    gapi.client.load('echo', 'v1', callback, ROOT);
}

listarEventos = function() {
   var emailStoraged = localStorage.getItem('usuario'); 
   document.title = emailStoraged;
  gapi.client.echo.echo.getEvents().execute(
      function(resp) {
        
    var table = document.getElementById("Events");
    var rowCount = table.rows.length;


            var row = table.insertRow(rowCount);

            var events = resp;
            for(var i = 0; i < events.items.length; i++)
            {
               var cellId= row.insertCell(0); 
                var cellNombre= row.insertCell(1); 
                var cellDiscoteca = row.insertCell(2);
               var cellFecha= row.insertCell(3); 
               var cellFechaFin= row.insertCell(4); 
                var cellPrecio= row.insertCell(5); 

                cellId.innerHTML = events.items[i].idEvento;
                cellNombre.innerHTML = '<a href= /Events/detallesEventos.html>'+ events.items[i].nombre+'</a>';
                cellDiscoteca.innerHTML = events.items[i].discotecaidDiscoteca.nombre;
                cellFecha.innerHTML = events.items[i].fechaInicio;
                cellFechaFin.innerHTML = events.items[i].fechaFinal;
                cellPrecio.innerHTML = events.items[i].precio;

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

    localStorage.setItem('idEvento',data[0]);
};


function setPageToFormulario(){
    window.location="/PayU/Formulario.html";
}
