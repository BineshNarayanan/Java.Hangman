<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>Qandidate Hangman</title>
	<link href="<c:url value="/js/bootstrap/css/bootstrap.min.css" />" rel="stylesheet">
</head>
<body>
	<div id="header" class="container table-responsive" align="center">
		<br>
		<br>
		<h1>Lets play a game of HANGMAN!!</h1>
		<br>
		<br>
		<table>
			<tr>
				<td><input class="btn btn-success" type="button" value="Overview" onclick="getOverview()"/></td>
				<td>&nbsp;</td>
				<td><input class="btn btn-success" type="button" value="New Game" onclick="startNewGame();"></input></td>
			</tr>
		</table>
		<br>
		<br>
	</div>
	<div id="body" class="container" align="center" style="display:block;">
		<h2>
			Guess the WORD!!
		</h2>
		<br>
		<h2>
			<span id="blankWord" class="label label-default"></span>
		</h2>
		<span id="playarea" style="display:none;">
			<h3>
				<span id="guessarea" style="display:none">
					<input type="text" name="inputLetter" id="inputLetterId" maxlength="1" size="1" autofocus="autofocus" autocomplete="off"></input>
					<button name="btnGuess" class="btn btn-success" type="button" onclick="guessLetter();">Guess?</button><br><br>
				</span>
				<span id="loseBlock" style="display:none">
					The word was <span id="correctWord" class="label label-info"></span><br><br>
					You <span class="label label-primary">LOSE</span><br><br>
				</span>
				<span id="winBlock" style="display:none">
					<br>
					You <span class="label label-primary">WIN</span><br><br>
				</span>
			</h3>				
			<h4>
				You have <span id="noOfAttemptLeft" class="label label-success"></span> attempts left.
			</h4>
			<h4>
				<span id="errorMessage" class="label label-warning"></span> 
			</h4>
		</span>
	</div>
	<div id="overviewBody" class="container table-responsive" align="center" style="display:none;">
		<table class="table table-bordered">
			<thead>
				<tr>
					<th>Id</th>
					<th>Word</th>
					<th>Status</th>
					<th></th>
				</tr>
			</thead>
			<tbody id="tblOverviewBody" align="center">
				
			</tbody>
		</table>
		
	</div>
	
	<input type="hidden" id="gameId" value=""></input>
</body>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script src="js/bootstrap/js/bootstrap.min.js"></script>
<script src="js/hangman.js"></script>
</html>
