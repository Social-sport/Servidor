$(document).ready(
    function() {

        $("#listSport").click(
            function(event) {

                event.preventDefault();
                $.ajax({
                    type : "GET",
                    url : "/Servidor/deportes",
                    data : $("#tipoDeport").serialize(),
                    dataType: "JSON",                    
                    success : function(listSport) {

                        $("#seccion1").html("<div><h3 id='seccion'>Deportes Disponibles</h3></div>");
                        
                    	$.each(listSport, function(i,item){                      

                            $("#seccion1").append("<form action='/Servidor/deportes' method='POST'  class='list-group-item active'  id='listSearchs'>"+
                                    
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

                    },
                    error : function() {                    	
                    	$("#seccion1").html("<div class='alert alert-danger lead'>Error: No encuentra deportes en base de datos</div>");
                    }
                });
        });
});