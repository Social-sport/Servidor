$(document).ready(
    function() {

        $("#BuscarUsuarios").submit(
            function(event) {
            	
            	$('.tab-content div.active').removeClass('active in');
            	$('#buscar').addClass('active in');
            	
            	$('.pager li.active').removeClass('active');
            	
                event.preventDefault();
                $.ajax({
                	type : "GET",
                    url : "/Servidor/usuarios",
                    data : $(this).serialize(),
                    dataType: "JSON",
                    success : function(search) {                    	
                    	$("#seccionBuscar").html("<div><h3 id='seccion'>Resultado de Busqueda</h3></div>");
                    	
                    	$.each(search, function(i,item){                    

                            $("#seccionBuscar").append("<form action='/Servidor/amigos' method='POST' class='list-group-item active'  id='listSearchs'>"+
                                    "<div class='media col-md-3'>"+
                                    "<figure class='pull-left'>"+
                                        "<img class='media-object img-rounded img-responsive'  src='"+ search[i].foto+"' alt='placehold.it/350x250' >"+
                                    "</figure>"+
                                "</div>"+
                                "<div class='col-md-6'>"+
                                    "<input type='hidden' name='tipoFriendSuscribe' value='SuscribeFriend' id='tipoFriendSuscribe'/>"+
                                    "<input type='hidden' name='emailAmigo' value='"+search[i].email+"'>"+
                                    "<h4 class='list-group-item-heading'>"+search[i].nombre+"</h4>"+
                                    "<p class='list-group-item-text'> "+search[i].apellidos+" </p>"+
                                "</div>"+
                                "<div class='col-md-3 text-center'>"+
                                    "<input type='submit' class='btn btn-default btn-lg btn-block'  id = 'bSeguir' value='Seguir'>"+
                                "<h5> "+search[i].numSeguidores +" <small> Seguidores </small></h5>"+
                                "</div>"+ 
                            "</form>");
                        });
                    },
                    error : function() {
                    	
                    	$("#seccion2,#seccionAmigos").html("<div><h3 id='seccion'>No Hay Resultados Para Esta Busqueda</h3></div>");
                    }
                });
                
                $.ajax({
                	type : "GET",
                    url : "/Servidor/eventos",
                    data : $(this).serialize(),
                    dataType: "JSON",
                    success : function(search) {                    	
                    	$("#seccionBuscar").html("<div><h3 id='seccion'>Resultado de Busqueda</h3></div>");
                    	
                    	$.each(search, function(i,item){                    

                            $("#seccionBuscar").append("<form action='/Servidor/eventos' method='GET'  class='list-group-item active'  id='listSearchs'>"+

            						"<div class='media col-md-3'>"+
            						"<figure class='pull-left'>"+
            						"<img class='media-object img-rounded img-responsive'  src='"+search[i].foto+"' alt='placehold.it/350x250' >"+
            						"</figure>"+
            						"</div>"+
            						"<div class='col-md-6'>"+
            						"<input type='hidden' name='idEvent' value='"+search[i].id+"'>"+
            						"<input type='hidden' name='tipo' value='viewEvent'>"+
            						"<h4 class='list-group-item-heading'>"+search[i].nombre+"</h4>"+
            						"<p class='list-group-item-text' id='list-group-item-text'> "+search[i].deporte+" </p>"+
            						"</div>"+
            						"<div class='col-md-3 text-center'>"+
            						"<input type='submit' class='btn btn-default btn-lg btn-block'  id = 'bSuscribete' value='Ver'>"+
            						"<h5> "+search[i].NumSuscritos+" <small> Asistentes </small></h5>"+
            						"</div>"+ 

            				"</form>");
                        });
                    },
                    error : function() {
                    	
                    	$("#seccion2,#seccionAmigos").html("<div><h3 id='seccion'>No Hay Resultados Para Esta Busqueda</h3></div>");
                    }
                });
        });
});