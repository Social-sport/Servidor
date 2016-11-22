$(document).ready(
    function() {

        $("#BuscarUsuarios").submit(
            function(event) {
                event.preventDefault();
                $.ajax({
                	type : "GET",
                    url : "/Servidor/usuarios",
                    data : $(this).serialize(),
                    dataType: "JSON",
                    success : function(search) {                    	
                    	$("#seccion2,#seccionAmigos").html("<div><h3 id='seccion'>Resultado de Busqueda</h3></div>");
                    	
                    	$.each(search, function(i,item){                    

                            $("#seccion2,#seccionAmigos").append("<div class='list-group' id='listDeportes'>"+
                                "<div class='list-group-item active' id='itemDeporte'>"+
                                "<div class='media col-md-3'>"+
                                "<figure class='pull-left'>"+
                                "<img class='media-object img-rounded img-responsive'  src="+search[i].foto +" alt='placehold.it/350x250' >"+
                                "</figure>"+
                                "</div>"+
                                "<div class='col-md-6'>"+
                                "<h4 class='list-group-item-heading'>"+search[i].nombre +"</h4>"+
                                "<p class='list-group-item-text'> "+search[i].apellidos +" </p></div>"+                                
                                "<div class='col-md-3 text-center'>"+
                                "<button type='button' class='btn btn-default btn-lg btn-block'  id = 'bSuscribete'> Seguir </button>"+
                                "<h5> 10 <small> Amigos </small></h5></div></div>");
                        });
                    },
                    error : function() {
                    	
                    	$("#seccion2,#seccionAmigos").html("<div><h3 id='seccion'>No Hay Resultados Para Esta Busqueda</h3></div>");
                    }
                });
        });
});