var user=null;

function initWelcome() {  
    //var ROOT = 'https://cali-planner.appspot.com/_ah/api';
    var ROOT_LOCAL = 'http://localhost:8080/_ah/api'; 
  // Loads the OAuth and helloworld APIs asynchronously, and triggers login
  // when they have completed.
    var apisToLoad;

    var callback = function() {
    	getUserLogin();
    }
    gapi.client.load('echo', 'v1', callback, ROOT_LOCAL);
}


getUserLogin = function(){
   var emailStoraged = localStorage.getItem('usuario'); 

	 gapi.client.echo.echo.getLoginAsistente({'correo':emailStoraged}).execute(
      function(resp) {

            if( !resp.error && resp!==false){
            	document.getElementById("welcome").innerHTML = "Bienvenido "+ resp.nombre;	
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



