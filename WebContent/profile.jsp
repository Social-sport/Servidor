<!DOCTYPE html>
<html lang="en">
  <head>
    <%@page pageEncoding="UTF-8"%> 
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.0/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://opensource.keycdn.com/fontawesome/4.6.3/font-awesome.min.css">
    <link rel="stylesheet" href="css/profile.css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css">

    <!-- <script src="js/profile.js"></script> -->
    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
    <script type="text/javascript" src="js/profile.js"></script>
    <script type="text/javascript" src="js/load.js"></script>
    <script type="text/javascript">
    function getUser(){
      var user='${sessionScope.nick}'
      alert("Start GET")

      $.ajax({
        type : "GET",
        url : "/Servidor/deportesSuscritos",
        success : function(msg) {
          if (msg.length == 0) {
            $("#seccion1").html("<img src='img/Sports.png' alt='Sports Bootstrap Theme'>");
          }
          else{
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
    }
    </script>

    <title>Perfil de Usuario</title>
  </head>
  <body onload="getUser();">
	<div id="topBar" ></div>
	
    <section>

      <div class="container" style="margin-top: 80px;">
        <div class="profile-head">

          <div class="col-md- col-sm-4 col-xs-12">
            <img src="http://www.newlifefamilychiropractic.net/wp-content/uploads/2014/07/300x300.gif" class="img-responsive"/>
            <h6>${sessionScope.nick}</h6>
          </div><!--col-md-4 col-sm-4 col-xs-12 close-->

          <div class="col-md- col-sm-4 col-xs-12" id="perfilInfo">
            <h5>${sessionScope.nombre} ${sessionScope.apellidos}</h5>
            <p>Deportista</p>
            <ul>
              <li><span class="glyphicon glyphicon-envelope"></span><a href="#" title="mail">${sessionScope.email}</a></li>
            </ul>
          </div>

        </div><!--profile-head close-->
      </div><!--container close-->

      <div id="sticky" class="container">
        <!-- Nav tabs -->
        <ul class="nav nav-tabs nav-menu" role="tablist">
          <li class="active">
            <a href="#profile" role="tab" data-toggle="tab">
              <i class="fa fa-home"></i> Perfil
            </a>
          </li>
          <li>
            <a href="#mySports" role="tab" data-toggle="tab" id="btnDeportes">
              <i class="fa fa-futbol-o"></i> Mis Deportes
            </a>
          </li>
          <li>
            <a href="#myEvents" role="tab" data-toggle="tab">
              <i class="fa fa-tasks"></i> Mis Eventos
            </a>
          </li>
          <li>
            <a href="#myFriends" role="tab" data-toggle="tab">
              <i class="fa fa-group"></i> Mis Amigos
            </a>
          </li>
          <li>
            <a href="#myMessages" role="tab" data-toggle="tab">
              <i class="fa fa-mail-forward"></i> Mis Mensajes
            </a>
          </li>
          <li>
            <a href="#createEvent" id="creaEvent" role="tab" data-toggle="tab">
              <i class="fa fa-calendar-plus-o"></i> Crear Evento
            </a>
          </li>
          <li id="configEdit1">
            <a href="#change" id="editDat" role="tab" data-toggle="tab">
              <i class="fa fa-edit"></i> Editar Perfil
            </a>
          </li>
        </ul><!--nav-tabs close-->

        <!-- Tab panes -->
        <div class="tab-content">

          <div class="tab-pane fade " id="mySports">
            <div class="container fom-main">
              <h2 class="register">Mis Deportes</h2>
              <div class="row" id="seccion1">
              </div><!--row close-->
            </div><!--container close -->          
          </div><!--tab-pane close-->

          <div class="tab-pane fade " id="myEvents">
            <div class="container fom-main">
              <div class="row">
                <div class="col-sm-12">
                    <h2 class="register">Mis Eventos</h2>
                </div><!--col-sm-12 close-->
              </div><!--row close-->

              <div class="col-xs-6 tab-paneEvent">
                <h2 class="Subtittle">Creados</h2>
                  <p>No has creado a ningun evento</p>
                  <a href="#createEvent" id="creaEvent" class="btn btn-primary" data-toggle="tab">Crear Evento</a>
              </div><!--column close-->
     
              <div class="col-xs-6 tab-paneEvent">
                <h2 class="Subtittle">Suscritos</h2>
                  <p>No te has suscrito a ningun evento</p>
                  <a href="muro.jsp" class="btn btn-primary">Ver Eventos</a>
              </div><!--column close-->

            </div><!--container close --> 
         </div><!--tab-pane close-->

          <div class="tab-pane fade " id="myMessages">
            <div class="container fom-main">
              <div class="row">
                <div class="col-sm-12">
                    <h2 class="register">Mis Mensajes</h2>
                </div><!--col-sm-12 close-->
             </div><!--row close-->
            </div><!--container close -->          
          </div><!--tab-pane close-->

          <div class="tab-pane fade " id="myFriends">
            <div class="container fom-main">
              <div class="row">
                <div class="col-sm-12">
                    <h2 class="register">Mis Amigos</h2>
                </div><!--col-sm-12 close-->
              </div><!--row close-->
            </div><!--container close -->          
          </div><!--tab-pane close-->

        
          <div class="tab-pane fade active in" id="profile">
            <div class="container">
              <div class="col-sm-11">
                <div class="hve-pro">
                  <p>Hello I’m Jenifer Smith, a leading expert in interactive and creative design specializing in the mobile medium. My graduation from Massey University with a Bachelor of Design majoring in visual communication.</p>
                </div><!--hve-pro close-->
              </div><!--col-sm-12 close-->
              <br clear="all" />

              <div class="row">
                <div class="col-md-12">
                  <h4 class="pro-title">Información de Usuario</h4>
                </div><!--col-md-12 close-->

                <div class="col-md-6">
                  <div class="table-responsive responsiv-table">
                    <table class="table bio-table">
                      <tbody>
                        <tr>      
                          <td>Nombre</td>
                          <td>: ${sessionScope.nombre}</td> 
                        </tr>
                        <tr>    
                          <td>Apellidos</td>
                          <td>: ${sessionScope.apellidos}</td>       
                        </tr>
                        <tr>    
                          <td>Edad</td>
                          <td>: ${sessionScope.fecha}</td>       
                        </tr>
                        <tr>    
                          <td>País</td>
                          <td>: España</td>       
                        </tr>
                      </tbody>
                    </table>
                  </div><!--table-responsive close-->
                </div><!--col-md-6 close-->

                <div class="col-md-6">
                  <div class="table-responsive responsiv-table">
                    <table class="table bio-table">
                      <tbody>
                        <tr>  
                          <td>Deporte:</td>
                          <td>: Fútbol</td> 
                        </tr>
                        <tr>    
                          <td>Experiencia</td>
                          <td>: No Disponible</td>       
                        </tr>
                        <tr>    
                          <td>Movil</td>
                          <td>: No Disponible</td>       
                        </tr>
                      </tbody>
                    </table>
                  </div><!--table-responsive close-->
                </div><!--col-md-6 close-->
              </div><!--row close-->
            </div><!--container close-->
          </div><!--tab-pane close-->

          <div class="tab-pane fade " id="createEvent">
      
            <div class="container fom-main">
            
              <div id="cEvent" ></div>
              
            </div><!--container close -->          
          </div><!--tab-pane close-->

          <div class="tab-pane fade" id="change">
            <div class="container fom-main">
              <div id="editData" ></div>
            </div><!--container close -->          
          </div><!--tab-pane close-->
        </div><!--tab-content close-->
      </div><!--container close-->
    </section><!--section close-->
  </body>
</html>