$(document).ready(
    function() {
    	$("#topBar").load("topBar.jsp");
    	
    	$("#creaEvent").click(
                function(event) {
                	
                	$("#cEvent").load("Event.jsp");
                	
        });
    	
    	$("#editDat").click(
                function(event) {
                	
                	$("#editData").load("personalData.jsp");
                	
        });
    	
    });