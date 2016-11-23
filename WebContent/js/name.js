$(document).ready(
    function() {    	
    	$.ajax({
        	type : "GET",
            url : "/Servidor/usuarios",            
            dataType: "JSON",
            success : function(ms) {
            	
            	document.getElementById("perfil").innerHTML = ms.nombre;  
            	document.getElementById("nickUserP").innerHTML = ms.nick; 
            	document.getElementById("fullNameP").innerHTML = ms.nombre + " " + ms.apellidos; 
            	document.getElementById("mailUserP").innerHTML = ms.email;
            	document.getElementById("nameP").innerHTML = ": " + ms.nombre;  
            	document.getElementById("lastNameP").innerHTML = ": " + ms.apellidos;  
            	document.getElementById("fechaP").innerHTML = ": " + ms.fecha_nacimiento; 
            	
            	$("#imgUserP").attr("src", ms.foto);
            	$("#mailUserEP").val(ms.email);
            },
            error : function() {           	
            		
              alert("problema con encontrar el usuario: sesion no valida");
              window.location.replace("/Servidor/");
            }
            
          });
    	
    	$("#cerrarSesion").click(
                function(event) {
                    event.preventDefault();
                    $.get('usuarios', {tipo:'closeSesion'}, function (data){
                    	window.location.replace("/Servidor/");
                    	});                    
    	
          });
    });