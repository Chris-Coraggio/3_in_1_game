//var mysql = require("mysql");
var express = require("express");
var app = express();
var port = process.env.PORT || 8080;

//var pool  = mysql.createPool(process.env.JAWSDB_URL);

var connect_4_games = {};

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

app.get("/connect_4_host/:host", function (req, res) {
	var host = req.params.host;
	console.log("New Connect 4: " + host);
	res.send("Game created!");
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

app.set("port", port);

app.listen(app.get("port"), function () {
  console.log("3_in_1_server listening on port " + port + "!")
})