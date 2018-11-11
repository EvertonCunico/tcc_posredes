var arrayLixeiras = [];

btnListar.on('click',function(){


	var listarAPI = "http://localhost:8080/filemgr-dk/lixeiras/";
	$.getJSON( listarAPI, {})
	.done(function( data ) {
		$('#table-1 tr').not(':first').remove();
		var html = '';
		for(var i = 0; i < data.d.length; i++)
		    html += '<tr><td>' + data.d[i].nome + '</td><td>' + 
		                         data.d[i].sensor1 + '</td><td>' +
		                         data.d[i].sensor2 + '</td><td>' + 
		                         data.d[i].sensor3 + '</td></tr>' ;
		$('#table-1 tr').first().after(html);
	});



});