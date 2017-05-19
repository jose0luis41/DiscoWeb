
 function initLogin() {  
    //var ROOT = 'https://cali-planner.appspot.com/_ah/api';
    var ROOT_LOCAL = 'http://localhost:8080/_ah/api'; 
  
    var apisToLoad;

    var callback = function() {

    }
    gapi.client.load('echo', 'v1', callback, ROOT_LOCAL);
}



validarIngresoAdministrador = function(){
var usernameAdmin = document.getElementById("usernameAdministrator").value;
var password = document.getElementById("passwordAdministrator").value;

 gapi.client.echo.echo.getLoginAdministrator({'correo':usernameAdmin}).execute(
      function(resp) {

            if( !resp.error && resp!==false){
            	if(resp.contrasena === password){

                    var user = resp;
                    localStorage.setItem('administrador',usernameAdmin);
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


validarIngresoAsistente = function(){
var username = document.getElementById("usernameAssistant").value;
var password = document.getElementById("passwordAssistant").value;

 gapi.client.echo.echo.getLoginAsistente({'correo':username}).execute(
      function(resp) {

            if( !resp.error && resp!==false){
                if(resp.contrasena === password){
                    localStorage.setItem('usuario',username);
                    setAssistantCount();
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

function setAssistantCount(){
    window.location = "/Assistant/assistantView.html";
}

function setCreateTable(){
    window.location="VistaEventos.html";
}

function setIndexPage(){
    window.location="index.html"
}






function getEventInformation(){

}


 