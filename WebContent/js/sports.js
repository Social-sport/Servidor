$(document).ready(
    function() {

        $("#listSport").click(
            function(event) {

                event.preventDefault();
                $.ajax({
                    type : "GET",
                    url : "/Servidor/deportes",
                    success : function(msg) {
                        var deportes = msg;
                        $("#seccion1").html("<div></div>");
                        for (var i = 0; i < deportes.length; i++){
                            var deporte = deportes[i];

                            $("#seccion1").append(
                                    "<form action='/Servidor/deportes' method='POST' class='list-group-item active' id='listDeportes'>"+
                                        "<div class='media col-md-3'>"+
                                            "<figure class='pull-left'>"+
                                                "<img class='media-object img-rounded img-responsive'  src='"+deporte.foto+"' alt='placehold.it/350x250' >"+
                                            "</figure>"+
                                        "</div>"+
                                        "<div class='col-md-6'>"+
                                            "<input type='hidden' name='deporte' value='"+deporte.nombre+"'>"+
                                            "<h4 class='list-group-item-heading'>"+deporte.nombre+"</h4>"+
                                            "<p class='list-group-item-text'> "+deporte.descripcion+" </p>"+
                                        "</div>"+
                                        "<div class='col-md-3 text-center'>"+
                                            "<input type='submit' class='btn btn-default btn-lg btn-block'  id = 'bSuscribete' value='Suscribete'>"+
                                        "<h5> 14240 <small> personas </small></h5>"+
                                        "</div>"+
                                    "</form>");
                        };

                    },
                    error : function(msg) {
                        $("#seccion1").html("<div class='error'>"+msg.mensaje+"</div>");
                    }
                });
        });
});
