$(document).ready(
    function() {
    	
    	$("#topBar").load("topBar.html");
    	
    	$("#SportsButton").click(
                function(event) {

                    event.preventDefault();

                    $.get('deportes', {tipoDeport:'AvailableSports'}, function (listSport){ 
                        	
                        	if (listSport.length == 0) {
                        		$("#seccionSports").html("<div><h3 id='seccion'>Ya estas Suscrito a todos los Deportes</h3></div>");
                      		}else{

                            $("#seccionSports").html("<div><h3 id='seccion'>Deportes Disponibles</h3></div>");
                            
                        	$.each(listSport, function(i,item){             

                                $("#seccionSports").append("<form action='/Servidor/deportes' method='POST'  class='list-group-item active'  id='listSearchs'>"+
                                        
                                        "<div class='media col-md-3'>"+
                                            "<figure class='pull-left'>"+
                                                "<img class='media-object img-rounded img-responsive'  src='"+listSport[i].Foto+"' alt='placehold.it/350x250' >"+
                                            "</figure>"+
                                        "</div>"+
                                        "<div class='col-md-6'>"+
                                            "<input type='hidden' name='tipoDeporte' value='SuscribeSports' id='nameDeport'/>"+
                                            "<input type='hidden' name='deporte' value='"+listSport[i].nombre+"'>"+
                                            "<h4 class='list-group-item-heading'>"+listSport[i].nombre+"</h4>"+
                                            "<p class='list-group-item-text' id='list-group-item-text'> "+listSport[i].Descripcion+" </p>"+
                                        "</div>"+
                                        "<div class='col-md-3 text-center'>"+
                                            "<input type='submit' class='btn btn-default btn-lg btn-block'  id = 'bSuscribete' value='Suscribete'>"+
                                        "<h5> 14240 <small> personas </small></h5>"+
                                        "</div>"+ 
                                        
                                    "</form>");
                            });

                      		};
                       
                    });
            });
    	
    	$("#NotificationButton").click(
                function(event) {

                    event.preventDefault();

                    $.get('notificaciones', function (listNotification){ 
                        	
                        	if (listNotification.length == 0) {
                        		$("#seccionNotification").html("<div><h3 id='seccion'>No tienes notificaciones</h3></div>");
                      		}else{

                      			$("#seccionNotification").html("<div><h3 id='seccion'>Notificaciones</h3></div>");
                            
                      			$.each(listNotification, function(i,item){
                        		
                      				if (listNotification[i].tipo === "Seguidor") {
                      					
                      					$("#seccionNotification").append("<form action='/Servidor/amigos' method='POST'  class='list-group-item active'  id='listSearchs'>"+
                                                
                                                "<div class='media col-md-3'>"+
                                                    "<figure class='pull-left'>"+
                                                        "<img class='media-object img-rounded img-responsive'  src='"+listNotification[i].foto+"' alt='placehold.it/350x250' >"+
                                                    "</figure>"+
                                                "</div>"+
                                                "<div class='col-md-6'>"+                                                    
                                                    "<input type='hidden' name='emailAmigo' value='"+listNotification[i].usuarioEnvia+"'>"+
                                                    "<h4 class='list-group-item-heading'>"+listNotification[i].nombre +"</h4>"+
                                                    "<p class='list-group-item-text' id='list-group-item-text'> "+listNotification[i].descripcion+" </p>"+
                                                "</div>"+
                                                "<div class='col-md-3 text-center'>"+
                                                    "<input type='submit' class='btn btn-default btn-lg btn-block'  id = 'bSeguir' value='Seguir'>"+
                                                    "<h5> "+listNotification[i].hora +" <small> Hora </small></h5>"+
                                                "</div>"+ 
                                                
                                            "</form>");
										
									}
                      				if (listNotification[i].tipo === "Evento") {
                      					
                      					$("#seccionNotification").append("<form action='/Servidor/eventos' method='POST'  class='list-group-item active'  id='listSearchs'>"+
                                                
                                                "<div class='media col-md-3'>"+
                                                    "<figure class='pull-left'>"+
                                                        "<img class='media-object img-rounded img-responsive'  src='"+listNotification[i].foto+"' alt='placehold.it/350x250' >"+
                                                    "</figure>"+
                                                "</div>"+
                                                "<div class='col-md-6'>"+
                                                    "<input type='hidden' name='tipo' value='events' id='events'/>"+                                                    
                                                    "<h4 class='list-group-item-heading'>"+listNotification[i].nombre+"</h4>"+
                                                    "<p class='list-group-item-text' id='list-group-item-text'> "+listNotification[i].Descripcion+" </p>"+
                                                "</div>"+
                                                "<div class='col-md-3 text-center'>"+
                                                    "<input type='submit' class='btn btn-default btn-lg btn-block'  id = 'bSuscribete' value='Asistir'>"+
                                                "</div>"+ 
                                                
                                            "</form>");
										
									}
                      				
                      			});

                      		};
                       
                    });
            });
    	
    	$("#addFriendsButton").click(
                function(event) {
                	
                    event.preventDefault();
                    
                    $.get('usuarios', {tipo:'ListAvailableUsers'}, function (ListSports){ 
                    	
                        	$("#seccionFriends").html("<div><h3 id='seccion'>Usuarios Registrados</h3></div>");
                        	
                        	$.each(ListSports, function(i,item){                

                                $("#seccionFriends").append("<form action='/Servidor/amigos' method='POST' class='list-group-item active'  id='listSearchs'>"+
                                        "<div class='media col-md-3'>"+
                                        "<figure class='pull-left'>"+
                                            "<img class='media-object img-rounded img-responsive'  src='"+ ListSports[i].foto+"' alt='placehold.it/350x250' >"+
                                        "</figure>"+
                                    "</div>"+
                                    "<div class='col-md-6'>"+
                                        "<input type='hidden' name='tipoFriendSuscribe' value='SuscribeFriend' id='tipoFriendSuscribe'/>"+
                                        "<input type='hidden' name='emailAmigo' value='"+ListSports[i].email+"'>"+
                                        "<h4 class='list-group-item-heading'>"+ListSports[i].nombre+"</h4>"+
                                        "<p class='list-group-item-text'> "+ListSports[i].apellidos+" </p>"+
                                    "</div>"+
                                    "<div class='col-md-3 text-center'>"+
                                        "<input type='submit' class='btn btn-default btn-lg btn-block'  id = 'bSeguir' value='Seguir'>"+
                                    "<h5> 140 <small> Seguidores </small></h5>"+
                                    "</div>"+ 
                                "</form>");
                            });
                       
                        
                    });
            });
    	
    });