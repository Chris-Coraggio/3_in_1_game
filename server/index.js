//var mysql = require("mysql");
var express = require("express");
var app = express();
var port = process.env.PORT || 8080;

//var pool  = mysql.createPool(process.env.JAWSDB_URL);

function checkPlayer(user, game) {
	if (game.host == user || game.client == user) {
		return true;
	}
	return false;
}

function changeTurn(game) {
	if (game.turn == game.host) {
		game.turn = game.client;
	}
	else {
		game.turn = game.host;
	}
}

app.get("/", function (req, res) {
    res.send("Hello World!");
});

var users = [];

app.get("/set_username/:old-:user", function(req, res) {
	// Delete old username
	var old = req.params.old;
	var index = users.indexOf(old);
	if (index > -1) {
		users.splice(index, 1);
	}
	
	// Check if new username is unique
	var user = req.params.user;
	if (users.indexOf(user) > -1) {
		console.log("Username not unique");
		res.send("Username not unique");
	}
	else {
		users.push(user);
		console.log(users);
		res.send("User added!");
	}
});

var connect_4_games = {};

app.get("/connect_4_host/:host", function (req, res) {
	var host = req.params.host;
	console.log("New Connect 4: " + host);
	res.send("Game created!");
	if (host in connect_4_games) {
		delete connect_4_games[host];
	}
	game = {
		host: host
		};
	connect_4_games[host] = game;
	console.log(connect_4_games);
});

app.get("/connect_4_join/:host/:client", function (req, res) {
	var host = req.params.host;
	var client = req.params.client;
	console.log("Connect 4: " + host);
	console.log("Being joined by " + client);
	if (host in connect_4_games) {
		var game = connect_4_games[host];
		res.send("Game joined!");
		game.client = client;
		game.turn = game.host;
		game.col = "-1";
		console.log(game);
	}
	else {
		console.log("Error: host not found");
		res.send("Error: host not found")
	}
});

app.get("/connect_4/:host/:user", function (req, res) {
	console.log("GET:");
	console.log(req.originalUrl);
	var host = req.params.host;
	var user = req.params.user;
	if (host in connect_4_games) {
		var game = connect_4_games[host];
		if (checkPlayer(user, game)) {
			console.log(connect_4_games[host]);
			res.json(connect_4_games[host]);
		}
		else {
			console.log("Player not found in game: " + user);
			res.send("Player not found in game: " + user);
		}
	}
	else {
		console.log("Error: host not found");
		res.send("Error: host not found");
	}		
	console.log();
});

app.post("/connect_4/:host/:user/:col", function (req, res) {
	console.log("POST:");
	console.log(req.originalUrl);
	var host = req.params.host;
	var user = req.params.user;
	if (host in connect_4_games) {
		var game = connect_4_games[host];
		if (checkPlayer(user, game)) {
			if (user == game.turn) {
				var col = req.params.col;
				game.col = col;
				changeTurn(game);
				console.log(connect_4_games[host]);
				res.send("Move successful!");
			}
			else {
				console.log("Not player's turn: " + user)
			}
		}
		else {
			console.log("Player not found in game: " + user);
			res.send("Player not found in game: " + user);
		}
	}
	else {
		console.log("Error: host not found");
		res.send("Error: host not found");
	}
	console.log();
});

var tic_tac_toe_games = {};

app.get("/tic_tac_toe_host/:host", function (req, res) {
	var host = req.params.host;
	console.log("New Tic Tac Toe: " + host);
	res.send("Game created!");
	if (host in tic_tac_toe_games) {
		delete tic_tac_toe_games[host];
	}
	game = {
		host: host
		};
	tic_tac_toe_games[host] = game;
	console.log(tic_tac_toe_games);
});

app.get("/tic_tac_toe_join/:host/:client", function (req, res) {
	var host = req.params.host;
	var client = req.params.client;
	console.log("Tic Tac Toe: " + host);
	console.log("Being joined by " + client);
	if (host in tic_tac_toe_games) {	
		var game = tic_tac_toe_games[host];
		res.send("Game joined!");
		game.client = client;
		game.turn = game.host;
		game.col = "-1";
		game.row = "-1";
		console.log(game);
	}
	else {
		console.log("Error: host not found");
		res.send("Error: host not found")
	}
});

app.get("/tic_tac_toe/:host/:user", function (req, res) {
	console.log("GET:");
	console.log(req.originalUrl);
	var host = req.params.host;
	var user = req.params.user;
	if (host in tic_tac_toe_games) {
		var game = tic_tac_toe_games[host];
		if (checkPlayer(user, game)) {
			console.log(tic_tac_toe_games[host]);
			res.json(tic_tac_toe_games[host]);
		}
		else {
			console.log("Player not found in game: " + user);
			res.send("Player not found in game: " + user);
		}
	}
	else {
		console.log("Error: host not found");
		res.send("Error: host not found");
	}		
	console.log();
});

app.post("/tic_tac_toe/:host/:user/:row-:col", function (req, res) {
	console.log("POST:");
	console.log(req.originalUrl);
	var host = req.params.host;
	var user = req.params.user;
	if (host in tic_tac_toe_games) {
		var game = tic_tac_toe_games[host];
		if (checkPlayer(user, game)) {
			if (user == game.turn) {
				var col = req.params.col;
				var row = req.params.row;
				game.col = col;
				game.row = row;
				changeTurn(game);
				console.log(tic_tac_toe_games[host]);
				res.send("Move successful!");
			}
			else {
				console.log("Not player's turn: " + user)
			}
		}
		else {
			console.log("Player not found in game: " + user);
			res.send("Player not found in game: " + user);
		}
	}
	else {
		console.log("Error: host not found");
		res.send("Error: host not found");
	}
	console.log();
});

//-----------------HANGMAN------------------
var hangman_games = {}; //stores all hangman games
						//key: host
						//value: game object

app.get("/hangman_host/:host", function (req, res) {
	var host = req.params.host;
	console.log("New Hangman: " + host);
	res.send("Game created!");
	if (host in hangman_games) {
		delete hangman_games[host];
	}
	game = {
		host: host,
		words: getWords()
		};
	connect_4_games[host] = game;
	console.log(connect_4_games);
});

app.get("/hangman_join/:host/:client", function (req, res) {
	var host = req.params.host;
	var client = req.params.client;
	console.log("Hangman: " + host);
	console.log("Being joined by " + client);
	if (host in hangman_games) {	
		var game = hangman_games[host];
		res.send("Game joined!");
		game.client = client;
		console.log(game);
	}
	else {
		console.log("Error: host not found");
		res.send("Error: host not found")
	}
});

app.post("/hangman_done/:host/:client/:score/:numErrors", function(req, res) {
	var host = req.params.host;
	var client = req.params.client;
	var score = req.params.score;
	var numErrors = req.params.numErrors;

	//determine winner
});

//called at the end of the game until there is a winner
app.get("/hangman/:host/:user", function (req, res) {
	console.log("GET:");
	console.log(req.originalUrl);
	var host = req.params.host;
	var user = req.params.user;
	if (host in hangman_games) {
		var game = hangman_games[host];
		if (checkPlayer(user, game)) {
			console.log(hangman_games[host]);
			res.json(hangman_games[host]);
		}
		else {
			console.log("Player not found in game: " + user);
			res.send("Player not found in game: " + user);
		}
	}
	else {
		console.log("Error: host not found");
		res.send("Error: host not found");
	}		
	console.log();
});

function getWords(){
	//grab random words
	var wordsList = ["moist", "monkey"];
	//randomize word list and grab top 20 or so
	return wordsList;
}


//-------------END HANGMAN------------------
app.set("port", port);

app.listen(app.get("port"), function () {
  console.log("3_in_1_server listening on port " + port + "!")
})