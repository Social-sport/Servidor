$(document).ready(
	function() {

		$.get('deportes', {tipoDeport:'ListUserSports'}, function (listSport){

				if (listSport.length == 0) {
            		$("#seccionSports").html("<h2 class='register'>No tienes Deportes suscritos</h2>");
            		
          		}else{
          			$("#seccionSports").html("<h2 class='register'>Deportes suscritos</h2>");
					$.each(listSport, function(i,item){

						$("#seccionSports").append("<form action='/Servidor/deportes' method='GET'  class='list-group-item active'  id='listSearchs'>"+

						"<div class='media col-md-3'>"+
						"<figure class='pull-left'>"+
						"<img class='media-object img-rounded img-responsive'  src='"+listSport[i].Foto+"' alt='placehold.it/350x250' >"+
						"</figure>"+
						"</div>"+
						"<div class='col-md-6'>"+
						"<input type='hidden' name='tipoDeport' value='DesSuscribe' id='nameDeport'/>"+
						"<input type='hidden' name='deporte' value='"+listSport[i].nombre+"'>"+
						"<h4 class='list-group-item-heading'>"+listSport[i].nombre+"</h4>"+
						"<p class='list-group-item-text'> "+listSport[i].Descripcion+" </p>"+
						"</div>"+
						"<div class='col-md-3 text-center'>"+
						"<input type='submit' class='btn btn-default btn-lg btn-block'  id = 'bSuscribete' value='Desuscribir'>"+
						"<h5> "+listSport[i].NumSuscritos+" <small> Suscritos </small></h5>"+
						"</div>"+ 

						"</form>");

					 });
          		};

                    	
        });
		
	});

