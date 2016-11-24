$(document).ready(
	function() {


		$.get('deportes', {tipoDeport:'ListUserSports'}, function (listSport){

				if (listSport.length == 0) {
            		$("#seccion1").html("<img src='img/Sports.png' alt='Sports Bootstrap Theme'>");
          		}else{
          			$("#seccion1").html("<div><h3 id='seccion'>Deportes Suscritos</h3></div>");
					$.each(listSport, function(i,item){

						$("#seccion1").append("<form action='/Servidor/deportes' method='GET'  class='list-group-item active'  id='listSearchs'>"+

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
						"<h5> 14240 <small> personas </small></h5>"+
						"</div>"+ 

						"</form>");

					 });
          		};

                    	
        });
		
	});

