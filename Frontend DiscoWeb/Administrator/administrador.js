
function initWelcomeAdmin() {  
    //var ROOT = 'https://cali-planner.appspot.com/_ah/api';
    var ROOT_LOCAL = 'http://localhost:8080/_ah/api'; 
  // Loads the OAuth and helloworld APIs asynchronously, and triggers login
  // when they have completed.
    var apisToLoad;

    var callback = function() {
    	getAdminLogin();
    }
    gapi.client.load('echo', 'v1', callback, ROOT_LOCAL);
}


getAdminLogin = function(){
   var emailAdminStoraged = localStorage.getItem('administrador'); 

	 gapi.client.echo.echo.getLoginAdministrator({'correo':emailAdminStoraged}).execute(
      function(resp) {

            if( !resp.error && resp!==false){
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