$(document).ready(
    function() {

        $("#listSport").click(
            function(event) {

                event.preventDefault();
                $.ajax({
                    type : "GET",
                    url : "/Servidor/deportes",
                    dataType: "JSON",                    
                    success : function(listSport) {

                        $("#seccion1").html("<div></div>");
                    	
                    	$.each(listSport, function(i,item){                      

                            $("#seccion1").append("<div class='list-group' id='listDeportes'>"+                                
                                "<div class='list-group-item active' id='itemDeporte'>"+
                                "<div class='media col-md-3'>"+
                                "<figure class='pull-left'>"+
                                "<img class='media-object img-rounded img-responsive'  src="+listSport[i].Foto +" alt='placehold.it/350x250' >"+
                                "</figure>"+
                                "</div>"+
                                "<div class='col-md-6'>"+
                                "<h4 class='list-group-item-heading'>"+listSport[i].nombre +"</h4>"+
                                "<p class='list-group-item-text'> "+listSport[i].Descripcion +" </p></div>"+                                
                                "<div class='col-md-3 text-center'>"+
                                "<button type='button' class='btn btn-default btn-lg btn-block'  id = 'bSuscribete'> Suscribete </button>"+
                                "<h5> 14240 <small> personas </small></h5></div></div>");
                        });

                    },
                    error : function() {                    	
                    	$("#seccion1").html("<div class='alert alert-danger lead'>Error: No encuentra deportes en base de datos</div>");
                    }
                });
        });
});