$(document).ready(
		function() {

			try {
				$.get('usuarios', {
					tipo:'infosesion'
				}, function(ms) {
					var dateNow = new Date().toJSON().slice(0, 10).split('-');
					var dateUser = new Date(ms.fecha_nacimiento).toJSON().slice(0, 10).split('-');
					var edad = dateNow[0] - dateUser[0];

					document.getElementById("username").innerHTML = ms.nombre;
					document.getElementById("nickUserP").innerHTML = ms.nick;
					document.getElementById("fullNameP").innerHTML = ms.nombre
							+ " " + ms.apellidos;
					document.getElementById("mailUserP").innerHTML = ms.email;
					document.getElementById("nameP").innerHTML = ": "
							+ ms.nombre;
					document.getElementById("lastNameP").innerHTML = ": "
							+ ms.apellidos;
					document.getElementById("edadP").innerHTML = ": "
							+ edad;

					$("#imgUserP").attr("src", ms.foto);
					$("#mailUserEP").val(ms.email);

				});
			} catch (err) {

				window.location.replace("/Servidor/");

			}

			$("#cerrarSesion").click(function(event) {
				event.preventDefault();
				$.get('usuarios', {
					tipo : 'closeSesion'
				}, function(data) {
					window.location.replace("/Servidor/");
				});

			});

		});