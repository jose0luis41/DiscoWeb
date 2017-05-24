var user=null;

function initWelcome() {  
    //var ROOT = 'https://disco-web.appspot.com/_ah/api';
    var ROOT_LOCAL = 'http://localhost:8080/_ah/api'; 
  
  
    var apisToLoad;

    var callback = function() {
    	getUserLogin();
        getDiscos();
    }
    gapi.client.load('echo', 'v1', callback, ROOT_LOCAL);
    //gapi.client.load('echo', 'v1', callback, ROOT);
}


getUserLogin = function(){
   var emailStoraged = localStorage.getItem('usuario'); 

	 gapi.client.echo.echo.findAssistantByCorreo({'correo':emailStoraged}).execute(
      function(resp) {

            if( !resp.error && resp!==false){
            	document.getElementById("welcome").innerHTML = "¡Bienvenido "+ resp.nombre+"!";	
            	document.getElementById("userName").innerHTML = resp.nombre;	

            	document.title = resp.nombre;
            	return resp;
            }else if(resp.code ===400){
                    alert(resp.error);
            }else{
                alert("Error "+ resp.error);
            } 
    });

}


getDiscos= function(){

     gapi.client.echo.echo.getDiscos().execute(
      function(resp) {

            if( !resp.error && resp!==false){
                 var table = document.getElementById("tableDisco");
                 var rowCount = table.rows.length;
                 var row = table.insertRow(rowCount);

                 var discos = resp;

            for(var i = 0; i < discos.items.length; i++)
            {
               var cellId= row.insertCell(0); 
                var cellNombre= row.insertCell(1); 
               

                cellId.innerHTML = discos.items[i].idDiscoteca;
                cellNombre.innerHTML = discos.items[i].nombre;

                row = table.insertRow(table.rows.length);

            }


            }else if(resp.code ===400){
                    alert(resp.error);
            }else{
                alert("Error "+ resp.error);
            } 
    });

}


function nobackbutton(){

   window.location.hash="no-back-button";

   window.location.hash="Again-No-back-button" //chrome

   window.onhashchange=function(){window.location.hash="no-back-button";}
}




