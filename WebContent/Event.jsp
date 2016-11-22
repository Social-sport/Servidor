<!DOCTYPE html>
<html lang="en">
  <head>
 	  <%@page pageEncoding="UTF-8"%> 
    <title>Event</title>
  </head>
  <body>
  		      <div class="row">
                <div class="col-sm-12">
                  <h2 class="register">Crear Evento</h2>
                </div><!--col-sm-12 close-->
              </div><!--row close-->

              <br />
              <div class="row">
                <form class="form-horizontal main_form text-left" action="/Servidor/eventos" method="post"  id="event_form">
                  <fieldset>
                    <div class="form-group col-md-12">
                      <label class="col-md-10 control-label">Nombre del evento</label>  
                      <div class="col-md-12 inputGroupContainer">
                        <div class="input-group">
                          <input  name="nombreEvento" placeholder="nombre del evento" id="nombre" class="form-control"  type="text">
                        </div>
                      </div>
                    </div>

                    <!-- Text input-->
                    <div class="form-group col-md-12">
                      <label class="col-md-10 control-label" >Hora Evento</label> 
                      <div class="col-md-12 inputGroupContainer">
                        <div class="input-group">
                          <input name="hora" placeholder="10:00 a.m." id="hora" class="form-control"  type="text">
                        </div>
                      </div>
                    </div>

                     <!-- Date input-->
                    <div class="form-group col-md-12">
                      <label class="col-md-10 control-label" >Fecha de Evento</label> 
                      <div class="col-md-12 inputGroupContainer">
                        <div class="input-group input-gs">
                          <input type="date" name="fechaE" id="fecha" placeholder="DD/MM/AAAA" class="form-control">
                        </div>
                      </div>
                    </div>

                    <!-- Text input-->
                    <div class="form-group col-md-12">
                      <label class="col-md-10 control-label">Descripsión</label>  
                      <div class="col-md-12 inputGroupContainer">
                        <div class="input-group">
                          <textarea class="form-control" name="descripcion"  id="descripcion" placeholder="campeonato de ....,
                          zona, calle, centro deportivo,Número de Contacto,Experiencia Requerida, Gratuito?
                          "></textarea>
                        </div>
                      </div>
                    </div>

                    <div class="form-group col-md-12"> 
                      <label class="col-md-10 control-label">Elegir Deporte</label>
                      <div class="col-md-12 selectContainer">
                        <div class="input-group input-gs">
                          <select name="deportes" class="form-control selectpicker" >
                            <option>---</option>
                            <option>Fútbol</option>
                            <option>Baloncesto</option>
                            <option>Ajedrez</option>
                          </select>
                        </div>
                      </div>
                    </div>

                    <!-- Button -->
                    <div class="form-group col-md-10">
                      <div class="col-md-6">
                        <input type="submit" name="event-submit" id="event-submit" class="btn btn-primary" value="Crear">
                        <a href="#myEvents" class="btn btn-primary" data-toggle="tab">Cancelar</a> 
                      </div>
                    </div>
                  </fieldset>
                </form>
              </div><!--row close-->
     
  	
   </body>
</html>