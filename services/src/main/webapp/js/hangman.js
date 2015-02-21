function getProgress(id){
	$.ajax({
		url:'webapi/hangman/games/'+id,
		type:'GET',
		data: '',
		dataType: 'json',
		contentType: "application/json; charset=utf-8",
		success:function(res){
			//alert("it works!");
			$("#blankWord").html(res.word);
			$("#noOfAttemptLeft").html(res.nooftries);
			if(res.status == 0 && res.nooftries == 0){
				$("#loseBlock").attr('style','display:block');
				$("#correctWord").html(res.originalword);
				$("#guessarea").attr('style','display:none');
			} else if(res.status == 1){
				$("#winBlock").attr('style','display:block');
				$("#guessarea").attr('style','display:none');
			}
			if(res.nooftries < 4){
				$('#noOfAttemptLeft').removeClass('label-success').addClass('label-danger');
			}
		},
		error:function(res){
			alert("Bad thing happend! " + res.statusText);
		}
	});
}

function getOverview(){
	$.ajax({
		url:'webapi/hangman/games',
		type:'GET',
		data: '',
		dataType: 'json',
		contentType: "application/json; charset=utf-8",
		success:function(res){
			//alert("it works!");
			$("#tblOverviewBody").html('');
			for (var i = 0; i < res.games.length; i++) {
				var row = $("<tr class='warning'/>");
				rowData = res.games[i];
				if(rowData.status == 'Success') {
					row = $("<tr class='success'/>");		
				} else if(rowData.status == 'Fail') {
					row = $("<tr class='danger'/>");
				}
			    $("#tblOverviewBody").append(row);
				row.append($("<td>" + rowData.id + "</td>"));
			    row.append($("<td>" + rowData.word + "</td>"));
			    row.append($("<td>" + rowData.status + "</td>"));
			    if(rowData.status == 'Busy') {
			    	row.append($("<td><input type='button' class='btn btn-success' value='Continue' onClick='continueGame("+rowData.id+");'></input></td>"));
			    } else {
			    	row.append($("<td><input type='button' class='btn btn-success' value='View' onClick='continueGame("+rowData.id+");'></input></td>"));	
			    }
			    
			}
			$("#overviewBody").attr('style','display:block');
			$("#body").attr('style','display:none');
		},
		error:function(res){
			alert("Bad thing happend! " + res.statusText);
		}
	});
}
function guessLetter() {
	$.ajax({
		url:'webapi/hangman/games/'+$("#gameId").val(),
		type:'POST',
		data: $("#inputLetterId").val(),
		dataType: 'json',
		contentType: "application/json; charset=utf-8",
		success:function(res){
			//alert("it works!");
			getProgress($("#gameId").val());
			if(res.errorMessage != '') {
				$("#errorMessage").html(res.errorMessage);	
			} else {
				$("#errorMessage").html('');
			}
		},
		error:function(res){
			alert("Bad thing happend! " + res.statusText);
		}
	});
	$("#inputLetterId").val('');
	$("#inputLetterId").focus();
}

function startNewGame() {
	$("#errorMessage").html('');
	$("#loseBlock").attr('style','display:none');
	$("#winBlock").attr('style','display:none');
	$("#correctWord").html('');
	$("#overviewBody").attr('style','display:none');
	$("#body").attr('style','display:block');
	$.ajax({
		url:'webapi/hangman/games',
		type:'POST',
		data: '',
		dataType: 'json',
		contentType: "application/json; charset=utf-8",
		success:function(res){
			//alert("it works!");
			$('#noOfAttemptLeft').removeClass('label-danger').addClass('label-success');
			$("#playarea").attr('style','display:block');
			$("#guessarea").attr('style','display:block');
			
			$("#gameId").val(res);
			getProgress(res);
		},
		error:function(res){
			alert("Bad thing happend! " + res.statusText);
		}
	});
}

function continueGame(id) {
	$("#errorMessage").html('');
	$("#loseBlock").attr('style','display:none');
	$("#winBlock").attr('style','display:none');
	$("#correctWord").html('');
	$("#overviewBody").attr('style','display:none');
	$("#body").attr('style','display:block');
	getProgress(id);
	$('#noOfAttemptLeft').removeClass('label-danger').addClass('label-success');
	$("#playarea").attr('style','display:block');
	$("#guessarea").attr('style','display:block');
	$("#gameId").val(id);
}