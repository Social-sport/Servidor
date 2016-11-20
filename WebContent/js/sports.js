$(document).ready(
    function() {

        $("#listSport").click(
            function(event) {

                event.preventDefault();
                $.ajax({
                    type : "GET",
                    url : "/Servidor/deportes",
                    //data : String,
                    success : function(ms) {
                    	alert(ms);
                        /*for (var i =  0; i <= msg.lenght; i++) {

                            $("#result").addend("<div class='list-group' id='listDeportes'>"+
                                "<a href='#' class='list-group-item active' id='itemDeporte'>"+
                                "<a href='#' class='list-group-item active' id='itemDeporte'>"+
                                "<div class='media col-md-3'>"+
                                "<figure class='pull-left'>"+
                                "<img class='media-object img-rounded img-responsive'  src="+msg.img +" alt='placehold.it/350x250' >"+
                                "</figure>"+
                                "</div>"+
                                "<div class='col-md-6'>"+
                                "<h4 class='list-group-item-heading'>"+msg.name +"</h4>"+
                                "<p class='list-group-item-text'> "+msg.Descrip +" </p></div>"+                                
                                "<div class='col-md-3 text-center'>"+
                                "<button type='button' class='btn btn-default btn-lg btn-block'  id = 'bSuscribete'> Suscribete </button>"+
                                "<h5> 14240 <small> personas </small></h5></div></a>");
                        };*/

                    },
                    error : function() {
                    	
                        $("#seccion1").load("/Servidor/listSports.html");

                    }
                });
        });
});
