$(document).ready(
    function() {
    	
    	$("#topBar").load("topBar.html");
    	
    	$("#mySportsButton").click(
                function(event) {

                    event.preventDefault();
                    
                    $.get('amigos', {tipoRelacion:'listSeguidos'}, function (listUsers){   

            			if (listUsers.length == 0) {
                    		$("#seccionFollowed").html("<h2 class='register'>No tienes Seguidos</h2>");
                  		}else{
                  			$("#seccionFollowed").html("<h2 class='register'>Seguidos</h2>");
            				$.each(listUsers, function(i,item) {

            					$("#seccionFollowed").append("<form action='/Servidor/amigos' method='DELETE'  class='list-group-item active'  id='listSearchs'>"+

            					"<div class='media col-md-3'>"+
            					"<figure class='pull-left'>"+
            					"<img class='media-object img-rounded img-responsive'  src='"+listUsers[i].foto+"' alt='placehold.it/350x250' >"+
            					"</figure>"+
            					"</div>"+
            					"<div class='col-md-6'>"+
            					"<input type='hidden' name='tipoDeport' value='DesSuscribe' id='nameDeport'/>"+
            					"<input type='hidden' name='deporte' value='"+listUsers[i].email+"'>"+
            					"<h4 class='list-group-item-heading'>"+listUsers[i].nombre+"</h4>"+
            					"<p class='list-group-item-text'> "+listUsers[i].apellidos+" </p>"+
            					"</div>"+
            					"<div class='col-md-3 text-center'>"+
            					"<input type='submit' class='btn btn-default btn-lg btn-block'  id = 'bSuscribete' value='Eliminar'>"+
            					"<h5> 10 <small> seguidores </small></h5>"+
            					"</div>"+ 

            					"</form>");

            				 });
                  		};

                            	
            		});
    	 
    	    	
         });
    });