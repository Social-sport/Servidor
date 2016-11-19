La aplicación tiene una arquitectura MVC donde las vistas, en vez de estar en la carpeta src vista, están en WebContent para poder desplegarla en tomcat y no duplicar los ficheros.
Para ejecutar la aplicación realizar gradle build y se crea un archivo .war en la carpeta webapps. Subir ese archivo a tomcat y lanzarlo (probado en apache-tomcat-7.0.65)
# Servidor
[![Build Status](https://travis-ci.org/UNIZAR-30248-2016-SocialSport/Servidor.svg)](https://travis-ci.org/UNIZAR-30248-2016-SocialSport/Servidor)

Para crear el war realizar gradle build o gradle war, subido en:
http://pruebaopenshift-socialsport.rhcloud.com/Servidor/