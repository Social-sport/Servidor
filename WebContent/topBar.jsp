<!DOCTYPE html>
<html lang="en">
  <head>
    <%@page pageEncoding="UTF-8"%> 
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.0/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://opensource.keycdn.com/fontawesome/4.6.3/font-awesome.min.css">
    <link rel="stylesheet" href="css/profile.css">

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>

    <title>Perfil de Usuario</title>
  </head>
  <body>
  	<div class="navbar navbar-default navbar-fixed-top" role="navigation">
      <div class="container">
        <div class="navbar-header">
          <button type="button" class="navbar-toggle" data-toggle="collapse" data-target =".navbar-collapse">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <a class="navbar-brand" href="muro.jsp">Social Sport</a>
        </div>
        <div class="navbar-collapse collapse">
          <ul class="nav navbar-nav navbar-right">
            <li id="busAmi">
              <form class="form-inline" role="form">
                <div class="col-sm-8 col-xs-8 form-group top_search" style="padding-right:0px;">
                  <input type="text" class="form-control search-wrap" id="search" placeholder="Busca aquí...">
                </div>
                <div class="col-sm-4 col-xs-4 form-group top_search" style="padding-left:0px;">
                  <button type="submit" class="btn btn-default search-btn">BUSCAR</button>
                </div>
              </form>
            </li>
            <li>
            </li>
            <li>
              <a href="muro.jsp" role="tab">Inicio</a>
            </li>
            <li>
              <a href="#" role="tab">Acerca de..</a>
            </li>
            <li>
              <a href="profile.jsp" role="tab">Perfil</a>
            </li>
            <li> 
              <form action="/Servidor/usuarios" method="GET" class="btn-logout">
                <input type="submit" name="login-submit" id="logout-submit" class="form-control" value="Cerrar Sesión">
              </form>
            </li>
          </ul>
        </div>
      </div>
    </div>
 
  </body>
</html>