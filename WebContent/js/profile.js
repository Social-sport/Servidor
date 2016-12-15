//tab js//
$(document).ready(function(e) {
	
	$("#topBar").load("topBar.html");
	
	
	$("#creaEvent").click(
            function(event) {
            	
            	$("#cEvent").load("Event.html");
            	
    });
	
	$("#editDat").click(
            function(event) {
            	
            	$("#editData").load("personalData.html");
            	
    });
	
	$("#FollowedButton").click(
            function(event) {

                event.preventDefault();
                
                $.get('amigos', {tipoRelacion:'listSeguidos'}, function (listUsers){   

        			if (listUsers.length == 0) {
                		$("#seccionFollowed").html("<h2 class='register'>No tienes Seguidos</h2>");
              		}else{
              			$("#seccionFollowed").html("<h2 class='register'>Seguidos</h2>");
        				$.each(listUsers, function(i,item) {

        					$("#seccionFollowed").append("<form action='/Servidor/amigos' method='DELETE'  class='list-group-item active'  id='listSearchs'>"+

        					"<div class='media col-md-3'>"+
        					"<figure class='pull-left'>"+
        					"<img class='media-object img-rounded img-responsive'  src='"+listUsers[i].foto+"' alt='placehold.it/350x250' >"+
        					"</figure>"+
        					"</div>"+
        					"<div class='col-md-6'>"+
        					"<input type='hidden' name='tipoDeport' value='DesSuscribe' id='nameDeport'/>"+
        					"<input type='hidden' name='deporte' value='"+listUsers[i].email+"'>"+
        					"<h4 class='list-group-item-heading'>"+listUsers[i].nombre+"</h4>"+
        					"<p class='list-group-item-text'> "+listUsers[i].apellidos+" </p>"+
        					"</div>"+
        					"<div class='col-md-3 text-center'>"+
        					"<input type='submit' class='btn btn-default btn-lg btn-block'  id = 'bSuscribete' value='Eliminar'>"+
        					"<h5> "+listUsers[i].numSeguidores +" <small> Seguidores </small></h5>"+
        					"</div>"+ 

        					"</form>");

        				 });
              		};

                        	
        		});
	 
	    	
     });
	
	
  $('.form').find('input, textarea').on('keyup blur focus', function (e) {
    var $this = $(this), label = $this.prev('label');

    if (e.type === 'keyup') {
      if ($this.val() === '') {
        label.removeClass('active highlight');
      } 
      else {
        label.addClass('active highlight');
      }
    } 
    else if (e.type === 'blur') {
      if( $this.val() === '' ) {
        label.removeClass('active highlight'); 
      } 
      else {
		    label.removeClass('highlight');   
			}   
    } 
    else if (e.type === 'focus') {
      if( $this.val() === '' ) {
    		label.removeClass('highlight'); 
			} 
      else if( $this.val() !== '' ) {
		    label.addClass('highlight');
			}
    }
  });

  $('.tab a').on('click', function (e) {
    e.preventDefault();
    $(this).parent().addClass('active');
    $(this).parent().siblings().removeClass('active');
    target = $(this).attr('href');
    $('.tab-content > div').not(target).hide();
    $(target).fadeIn(600);
  });

  //canvas off js//
  $('#menu_icon').click(function(){
    if($("#content_details").hasClass('drop_menu'))
		{
      $("#content_details").addClass('drop_menu1').removeClass('drop_menu');
    }
		else{
			$("#content_details").addClass('drop_menu').removeClass('drop_menu1');
    }
  });

  //search box js//
  $("#flip").click(function(){
    $("#panel").slideToggle("5000");
  });

  // sticky js//
  $(window).scroll(function(){
    if ($(window).scrollTop() >= 500) {
      $('nav').addClass('stick');
    }
    else {
       $('nav').removeClass('stick');
    }
  });
});
