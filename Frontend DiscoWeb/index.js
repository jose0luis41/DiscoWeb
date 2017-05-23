
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

 gapi.client.echo.echo.loginAdministrator({'correo':usernameAdmin}).execute(
      function(resp) {

            if( !resp.error && resp!==false){
                var tokenStoragedAdmin = localStorage.setItem('tokeAdmin',resp.token);
                

        gapi.client.echo.echo.findAdministratorByCorreo({'correo':usernameAdmin}).execute(
            function(respAdmin) {


            	if(respAdmin.contrasena === password){

                    localStorage.setItem('administrador',usernameAdmin);
                    setAdministratorCount();
            	}else{
            		  alert("Contrasena incorrecta");
            	}
                  });

            }else if(resp.code ===400){
                    alert(resp.error);
            }else if(resp.code==503){
                alert('Error backend'+ resp.error);
            }
            else{
                alert("Error "+ resp.error);
            }
         
              
    });

		

}


validarIngresoAsistente = function(){
var username = document.getElementById("usernameAssistant").value;
var password = document.getElementById("passwordAssistant").value;

try{

 gapi.client.echo.echo.getLoginAsistente({'correo':username}).execute(
      function(resp) {

            if( !resp.error && resp!==false){

                var tokenStoragedAssistant = localStorage.setItem('tokeAssistant',resp.token);

                gapi.client.echo.echo.findAssistantByCorreo({'correo':username}).execute(
                    function(respAssistant) {


                    if(respAssistant.contrasena === password){
                        localStorage.setItem('usuario',username);
                        setAssistantCount();
                    }else{
                          alert("Contrasena incorrecta");
                    }

                }); 
            }else if(resp.code === 404){
                    alert("No se ha escrito el correo");
                    setIndexPage();
            }
            else if(resp.code === 503){
                alert('No se encuentra un usario con el correo: '+username );
                setIndexPage();
            }

            else {
                console.log(resp);
                setIndexPage();
                alert("Debes escribir un correo");

            }
         
              
    });
}
catch(e){
    alert('hola '+e.message);
}
        

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


 