$(document).ready(
    function() {

    	$("#listFriends").click(
            function(event) {
                event.preventDefault();
                $.ajax({
                	type : "GET",
                    url : "/Servidor/usuarios",
                    data : $("#tipoAmigo").serialize(),
                    dataType: "JSON",
                    success : function(search) {                    	
                    	$("#seccion2").html("<div><h3 id='seccion'>Usuarios Registrados</h3></div>");
                    	
                    	$.each(search, function(i,item){                    

                            $("#seccion2").append("<form action='/Servidor/amigos' method='POST' class='list-group-item active'  id='listSearchs'>"+
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
                                "<h5> 140 <small> Seguidores </small></h5>"+
                                "</div>"+ 
                            "</form>");
                        });
                    },
                    error : function() {                    	
                    	
                    	$("#seccion2").html("<div class='alert alert-danger lead'>Error: No encuentra Usuarios en base de datos</div>");
                    }
                });
        });
});