$(document).ready(
    function() {
    	$("#topBar").load("topBar.html");
    	
    	$("#creaEvent").click(
                function(event) {
                	
                	$("#cEvent").load("Event.html");
                	
        });
    	
    	$("#editDat").click(
                function(event) {
                	
                	$("#editData").load("personalData.html");
                	
        });
    	
    });