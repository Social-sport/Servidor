$(document).ready(
		function() {

			$("#topBar").load("topBar.html");
			
			var idEvent;
			
			$.ajax({
            	type : "GET",
                url : "/Servidor/eventos",
                data : {tipo:'Event'},
                dataType: "JSON",
                success : function(ms) {
                	
                	idEvent = ms.id;
                	
                	document.getElementById("nombreEvento").innerHTML = ms.nombre;
					document.getElementById("descripcionEvent").innerHTML = ms.descripcion;
					document.getElementById("fechaEvento").innerHTML = ms.fecha;
					document.getElementById("horaEvento").innerHTML = ms.hora;
					document.getElementById("deporteEvento").innerHTML = ms.deporte;
					//document.getElementById("creador").innerHTML = ms.creador;
					
					$("#imgEvent").attr("src", ms.foto);
					
					var pro = ms.propietario;
					
					if (pro == null) {
						document.getElementById("deleteEvent").style.display="none";
						document.getElementById("eventEdit").style.display="none";
					}else{
						document.getElementById("SuscribeButton").style.display="none";
					}					
					
                },
                error : function() {
                	
                	alert("error al cargar");
                }
            });
			
			$("#eventEdit").click(function(event) {
				$.ajax({
					type : "GET",
	                url : "/Servidor/eventos",
	                data : {tipo:'Event'},
	                dataType: "JSON",
	                success : function(ms) {
	                	
	                	$("#idEvento").val(ms.id);
	                	$("#nombre").val(ms.nombre);
	                	$("#hora").val(ms.hora);
	                	$("#fecha").val(ms.fecha);
	                	$("#descripcion").val(ms.descripcion);
	                	$("#foto").val(ms.foto);
	                	
	                	$.get('deportes', {tipoDeport:'ListUserSports'}, function (listSport){
		    				if (listSport.length == 0) {
		                		$("#deporte").html("<option value='"+ms.deporte+"'>"+ms.deporte+"</option>");
		                		
		              		}else{
		              			$("#deporte").html("<option value=''>Selecciona un Deporte</option>");
		    					$.each(listSport, function(i,item){
		
		    						$("#deporte").append("<option value='"+listSport[i].nombre+"'>"+listSport[i].nombre+"</option>");
		
		    					 });
		              		};    	
		            });
	                }
				});
			});
			
			$("#SuscribeButton").click(
					function(event) {

						event.preventDefault();
						
						$.ajax({
		                	type : "POST",
		                    url : "/Servidor/eventos",
		                    data : {idEvento:idEvent,tipoPostEvent:'Suscribirse'},
		                    dataType: "JSON",
		                    success : function(us) {
		                    	document.getElementById("SuscribeButton").innerHTML = us;
		                    	
		                    }
						
		                });
			});
			
			$("#deleteEvent").click(
					function(event) {

						event.preventDefault();
						
						$.ajax({
		                	type : "DELETE",
		                    url : "/Servidor/eventos",
		                    data : {idEvento:idEvent},
		                    dataType: "JSON",
		                    success : function(us) {
		                    	
		                    	alert("se ha eliminado el evento");
		                    },
		                    error : function() {
		                    	
		                    	alert("no se ha eliminado el evento");
		                    }
						
		                });
			});
			
			$("#eventInvit").click(
		            function(event) {

		                event.preventDefault();
		                
		                $.get('amigos', {tipoRelacion:'listSeguidos'}, function (listUsers){   

		        			if (listUsers.length == 0) {
		                		$("#seccionEvent").html("<h2 class='register'>No tienes Seguidos para invitar</h2>"+
		                				"<div>"+
		        						"	<div class='thumbnail'>"+
		        						"		<img src='img/people.jpg' alt='Sports Bootstrap Theme'>"+
		        						"	</div>"+
		        						"</div>");
		              		}else{
		              			$("#seccionEvent").html("<h2 class='register'>Invitar Seguidos</h2>");
		        				$.each(listUsers, function(i,item) {

		        					$("#seccionEvent").append("<form action='/Servidor/notificaciones' method='POST'  class='list-group-item active'  id='listEvent'>"+

		        					"<div class='media col-md-3'>"+
		        					"<figure class='pull-left'>"+
		        					"<img class='media-object img-rounded img-responsive'  src='"+listUsers[i].foto+"' alt='placehold.it/350x250' >"+
		        					"</figure>"+
		        					"</div>"+
		        					"<div class='col-md-6'>"+
		        					"<input type='hidden' name='tipo' value='Evento' id='tipoEvent'/>"+
		        					"<input type='hidden' name='emailRecibe' value='"+listUsers[i].email+"'>"+
		        					"<input type='hidden' name='idEvent' value='"+idEvent+"'>"+
		        					"<h4 class='list-group-item-heading'>"+listUsers[i].nombre+"</h4>"+
		        					"<p class='list-group-item-text'> "+listUsers[i].apellidos+" </p>"+
		        					"</div>"+
		        					"<div class='col-md-3 text-center'>"+
		        					"<input type='submit' class='btn btn-default btn-lg btn-block'  id = 'bSuscribete' value='Invitar'>"+
		        					"<h5> "+listUsers[i].numSeguidores +" <small> Seguidores </small></h5>"+
		        					"</div>"+ 

		        					"</form>");

		        				 });
		              		};

		                        	
		        		});
			 
			    	
		     });
			
		});