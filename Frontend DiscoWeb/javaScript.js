

function validar(){
var username = document.getElementById("username").value;
var password = document.getElementById("password").value;


	try{	

	$.get("http://localhost:8080/_ah/api/echo/v1/adminstrador/"+username, function(data, status){


        if(status === 'success'){
            if(data != null){
            	if(data.contrasena === password){
            		window.location = "Administrador.html";
            	}else{
            		  alert("Contrasena incorrecta");
            	}
            }
         
        }else{
        	alert("pasa algo");
        }
        
    });

		}catch(error){
			console.log("entro al catch");
			alert(error+"");	
		}

}





 