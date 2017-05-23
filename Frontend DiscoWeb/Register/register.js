function setFormularioAsistenteRegistro(){
	 window.location = "/Register/formularioAsistente.html";

}


function setFormularioAdministradorRegistro(){
	 window.location = "/Register/formularioAdministrador.html";
}



 function initRegistroUsuarios() {  
    var ROOT = 'https://disco-web.appspot.com/_ah/api';
    //var ROOT_LOCAL = 'http://localhost:8080/_ah/api'; 
  
    var apisToLoad;

    var callback = function() {

    }
    gapi.client.load('echo', 'v1', callback, ROOT);
}



crearAsistente = function(){

var name = document.getElementById("nombreRegistroAsistente").value;	
var id = document.getElementById("cedulaRegistroAsistente").value;
var cel = document.getElementById("celularRegistroAsistente").value;
var dateBirth = document.getElementById("fechaNaciRegistroAsistente").value;
var email = document.getElementById("correoRegistroAsistente").value;
var password = document.getElementById("passwordRegistroAsistente").value;
var passwordConfirm = document.getElementById("passwordConfirmAsistente").value;

if(password !== passwordConfirm){
	alert('los password no coinciden');
	return;
}

 gapi.client.echo.echo.createAssistant({'cedula':id, 'contrasena':password, 'correo':email, 'fechaNacimiento':dateBirth, 'nombre':name,
											'telefono': cel}).execute(
      function(resp) {

            if( !resp.error && resp!==false){
            	registroExitoso();
            }else if(resp.code ===400){
                    alert(resp.error);
            }else{
                alert("Error "+ resp.error);
            }
         
              
    });

		

}


crearAdministrador = function(){
var idDisco = document.getElementById("idDiscoteca").value;	
var name = document.getElementById("nombreRegistroAdmin").value;	
var id = document.getElementById("cedulaRegistroAdmin").value;
var cel = document.getElementById("celularRegistroAdmin").value;
var dateBirth = document.getElementById("fechaNaciRegistroAdmin").value;
var email = document.getElementById("correoRegistroAdmin").value;
var password = document.getElementById("passwordRegistroAdmin").value;
var passwordConfirm = document.getElementById("passwordConfirmRegistroAdmin").value;

if(password !== passwordConfirm){
	alert('los password no coinciden');
	return;
}

 gapi.client.echo.echo.createAdministrator({'cedula':id, 'contrasena':password, 'correo':email, 
 											'fechaNac':dateBirth, 'nombre':name,'telefono': cel,'idDiscoteca':idDisco}).execute(
      function(resp) {

            if( !resp.error && resp!==false){
            	registroExitoso();
            }else if(resp.code ===400){
                    alert(resp.error);
            }else{
                alert("Error "+ resp.error);
            }
         
              
    });
}

function registroExitoso(){
	window.location = "/index.html";
	alert('Registro exitoso');
}


