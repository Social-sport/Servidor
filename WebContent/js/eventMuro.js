$(document).ready(
    function() {

    	$.ajax({
        type : "GET",
        url : "/Servidor/deportesSuscritos",
        success : function(msg) {
          if (msg.length == 0) {
            document.getElementById("textEventDis").innerHTML = "";
            $("#seccion1").html("<img src='img/Sports.png' alt='Sports Bootstrap Theme'>");
          }
          else{
            document.getElementById("textEventDis").innerHTML = "Eventos disponibles de tus deportes suscritos";
            $("#seccion1").html("<div></div>");
            for (var i = 0; i < msg.length; i++){
              var deporte = msg[i];
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
                        "<h5> 14240 <small> personas </small></h5>"+
                        "</div>"+
                    "</form>");
            };
          };
        },
        error : function(msg) {
          $("#seccion1").html("<div>"+msg+"</div>");
        }
      });
    });
