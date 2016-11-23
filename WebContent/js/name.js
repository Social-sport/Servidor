$(document).ready(
    function() {    	
    	$.ajax({
        	type : "GET",
            url : "/Servidor/usuarios",            
            dataType: "JSON",
            success : function(ms) {
            	
            	document.getElementById("perfil").innerHTML = ms.nombre;            	
            },
            error : function() {           	
            		
              alert("problema con encontrar el usuario: sesion no valida");
            }
            
          });    	
    });