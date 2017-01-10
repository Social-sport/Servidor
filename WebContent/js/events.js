$(document).ready(
		function() {

			$("#topBar").load("topBar.html");
			
			$("#SuscribeButton").click(
					function(event) {

						event.preventDefault();
						
						$.ajax({
		                	type : "POST",
		                    url : "/Servidor/eventos",
		                    data : {idEvent:$("#id"),tipoPostEvent:'Suscribirse'},
		                    dataType: "JSON",
		                    success : function(us) {
		                    	
		                    	$("#SuscribeButton").value("Suscrito");
		                    },
		                    error : function() {
		                    	
		                    	$("#SuscribeButton").value("Suscribir");
		                    }
						
		                });
			});
		});