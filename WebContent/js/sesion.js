$(document).ready(
		function() {

			try {
				$.get('usuarios', {
					tipo:'infosesion'
				}, function(ms) {

					document.getElementById("username").innerHTML = ms.nombre;
					document.getElementById("nickUserP").innerHTML = ms.nick;
					document.getElementById("fullNameP").innerHTML = ms.nombre
							+ " " + ms.apellidos;
					document.getElementById("mailUserP").innerHTML = ms.email;
					document.getElementById("nameP").innerHTML = ": "
							+ ms.nombre;
					document.getElementById("lastNameP").innerHTML = ": "
							+ ms.apellidos;
					document.getElementById("fechaP").innerHTML = ": "
							+ ms.fecha_nacimiento;

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