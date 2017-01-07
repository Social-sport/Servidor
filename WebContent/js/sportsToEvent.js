$(document).ready(
	function() {

		$.get('deportes', {tipoDeport:'ListUserSports'}, function (listSport){

				if (listSport.length == 0) {
            		$("#deporte").html("<option value=''>Suscribete a Deportes</option>");
            		
          		}else{
          			$("#deporte").html("<option value=''>Selecciona un Deporte</option>");
					$.each(listSport, function(i,item){

						$("#deporte").append("<option value='"+listSport[i].nombre+"'>"+listSport[i].nombre+"</option>");

					 });
          		};

                    	
        });
		
	});