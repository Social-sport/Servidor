<!DOCTYPE html>
<html lang="en">
  <head>
  	<%@page pageEncoding="UTF-8"%> 
    <title>Event</title>
  </head>
  <body>
  		<div class="row">
                <div class="col-sm-12">
                  <h2 class="register">Edita tu Perfil</h2>
                </div><!--col-sm-12 close-->
              </div><!--row close-->

              <br />
              <div class="row">
                <form class="form-horizontal main_form text-left" action="/Servidor/usuarios" method="POST"  id="contact_form">
                  <fieldset>

                    <div class="form-group col-md-12">
                      <label class="col-md-10 control-label" >Email</label> 
                      <div class="col-md-12 inputGroupContainer">
                        <div class="input-group">
                          <input type="email" disabled="disabled" name="email" id="email" value="${sessionScope.email}"
                          class="form-control" >
                          <input type="hidden" name="tipo" id="tipo" tabindex="1" class="form-control" value="actualizar">
                        </div>
                      </div>
                    </div>

                    <div class="form-group col-md-12">
                      <label class="col-md-10 control-label">Nombre</label>  
                      <div class="col-md-12 inputGroupContainer">
                        <div class="input-group">
                          <input  name="nombre" id="nombre" placeholder="Nombre" class="form-control"  type="text">
                        </div>
                      </div>
                    </div>

                    <!-- Text input-->
                    <div class="form-group col-md-12">
                      <label class="col-md-10 control-label" >Apellidos</label> 
                      <div class="col-md-12 inputGroupContainer">
                        <div class="input-group">
                          <input name="apellidos" id="apellidos" placeholder="Primer Apellido" class="form-control"  type="text">
                        </div>
                      </div>
                    </div>
                    
                     <!-- Text input-->
                    <div class="form-group col-md-12">
                      <label class="col-md-10 control-label" >Fecha de Nacimiento</label> 
                      <div class="col-md-12 inputGroupContainer">
                        <div class="input-group input-gs">
                          <input type="date" name="fecha_nacimiento" id="fecha_nacimiento" class="form-control">
                        </div>
                      </div>
                    </div>
                   
                    <div class="form-group col-md-12">
                      <label class="col-md-10 control-label">Contraseña</label>  
                      <div class="col-md-12 inputGroupContainer">
                        <div class="input-group">
                          <input  name="contrasena" id="contrasena" class="form-control"  type="password">
                        </div>
                      </div>
                    </div>

                    <div class="form-group col-md-12">
                      <label class="col-md-10 control-label">Nueva Contraseña</label>  
                      <div class="col-md-12 inputGroupContainer">
                        <div class="input-group">
                          <input  name="newContrasena" id="newContrasena" placeholder="Nueva Contraseña" class="form-control"  type="password">
                        </div>
                      </div>
                    </div>

                    <div class="form-group col-md-12">
                      <label class="col-md-10 control-label">Confirmar Nueva Contraseña</label>  
                      <div class="col-md-12 inputGroupContainer">
                        <div class="input-group">
                          <input  name="confirmNewContrasena" id="confirmNewContrasena" placeholder="Confirmar Nueva Contraseña" class="form-control"  type="password">
                        </div>
                      </div>
                    </div>                    

                    <!-- upload profile picture -->
                    <div class="col-md-12 text-left">
                      <div class="uplod-picture">
                        <span class="btn btn-default uplod-file">
                          Subir foto<input type="file" name='foto' id='foto'/>
                        </span>
                      </div><!--uplod-picture close-->
                    </div><!--col-md-12 close-->

                    <!-- Button -->
                    <div class="form-group col-md-10">
                      <div class="col-md-6"> 
                        <input type="submit" name="update-submit" id="update-submit" 
                          class="btn btn-primary" value="Actualizar"> 
                        <a href="#profile" class="btn btn-primary" data-toggle="tab">Cancelar</a>                        
                      </div>
                    </div>
                  </fieldset>
                </form>
              </div><!--row close-->
  </body>
</html>