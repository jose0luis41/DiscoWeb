
 function initLogin() {  
    //var ROOT = 'https://cali-planner.appspot.com/_ah/api';
    var ROOT_LOCAL = 'http://localhost:8080/_ah/api'; 
  
    var apisToLoad;

    var callback = function() {

    }
    gapi.client.load('echo', 'v1', callback, ROOT_LOCAL);
}



validarIngresoAdministrador = function(){
var username = document.getElementById("usernameAdministrator").value;
var password = document.getElementById("passwordAdministrator").value;

 gapi.client.echo.echo.getLoginAdministrator({'correo':username}).execute(
      function(resp) {

            if( !resp.error && resp!==false){
            	if(resp.contrasena === password){
                    setAdministratorCount();
            	}else{
            		  alert("Contrasena incorrecta");
            	}
            }else if(resp.code ===400){
                    alert(resp.error);
            }else{
                alert("Error "+ resp.error);
            }
         
              
    });

		

}


function setAdministratorCount(){
 window.location = "/Administrator/administratorView.html";

}


function setCreateTable(){
    window.location="VistaEventos.html";
}

function setIndexPage(){
    window.location="index.html"
}




            
















function getEventInformation(){

}


 