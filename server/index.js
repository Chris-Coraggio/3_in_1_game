//var mysql = require("mysql");
var express = require("express");
var ip = require("ip");
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

app.get("/set_username/:user", function(req, res) {
	// Check if new username is unique
	var user = req.params.user;
	if (users.indexOf(user) > -1) {
		console.log("Username not unique");
		res.send("Username not unique");
	}
	else {
		users.push(user);
		console.log(users);
		res.send("Username added!");
	}
});

app.get("/set_and_delete_username/:old-:user", function(req, res) {
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
		res.send("Username added!");
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
		var turn;
		if (Math.random() >= 0.5) {
			turn = host;
		}
		else {
			turn = client;
		}
		var game = connect_4_games[host];
		res.send("Game joined!");
		game.client = client;
		game.turn = turn;
		game.col = "-1";
		console.log(game);
	}
	else {
		console.log("Error: host not found");
		res.send("Error: host not found")
	}
});

app.get("/connect_4_restart/:host/:user", function (req, res) {
	console.log("GET:");
	console.log(req.originalUrl);
	var host = req.params.host;
	var user = req.params.user;
	if (host in connect_4_games) {
		var game = connect_4_games[host];
		if (checkPlayer(user, game)) {
			console.log(connect_4_games[host]);
			if ("state" in game && game.state == "RESTART") {
				delete game.state;
				res.send("State removed!");
			}
			else {
				game.state = "RESTART";
				game.col = "-1";
				res.send("State set to RESTART!");
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
		var turn;
		if (Math.random() >= 0.5) {
			turn = host;
		}
		else {
			turn = client;
		}		
		var game = tic_tac_toe_games[host];
		res.send("Game joined!");
		game.client = client;
		game.turn = turn;
		game.col = "-1";
		game.row = "-1";
		console.log(game);
	}
	else {
		console.log("Error: host not found");
		res.send("Error: host not found")
	}
});

app.get("/tic_tac_toe_restart/:host/:user", function (req, res) {
	console.log("GET:");
	console.log(req.originalUrl);
	var host = req.params.host;
	var user = req.params.user;
	if (host in tic_tac_toe_games) {
		var game = tic_tac_toe_games[host];
		if (checkPlayer(user, game)) {
			console.log(tic_tac_toe_games[host]);
			if ("state" in game && game.state == "RESTART") {
				delete game.state;
				res.send("State removed!");
			}
			else {
				game.state = "RESTART";
				game.col = "-1";
				game.row = "-1";
				res.send("State set to RESTART!");
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
	var words = getWords();
	game = {
		host: host,
		words: words
		};
	hangman_games[host] = game;
	console.log(hangman_games);
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

app.get("/hangman_restart/:host/:user", function (req, res) {
	console.log("GET:");
	console.log(req.originalUrl);
	var host = req.params.host;
	var user = req.params.user;
	if (host in hangman_games) {
		var game = hangman_games[host];
		if (checkPlayer(user, game)) {
			console.log(hangman_games[host]);
			if ("state" in game && game.state == "RESTART") {
				delete game.state;
				delete game.client_errors;
				delete game.client_score;
				delete game.host_errors;
				delete game.host_score;
				res.send("State removed!");
			}
			else {
				game.state = "RESTART";
				var words = getWords();
				game.words = words;
				delete game.winner;
				res.send("State set to RESTART!");
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

app.post("/hangman_done/:host/:client/:score/:numErrors", function(req, res) {
	var host = req.params.host;
	var client = req.params.client;
	var score = req.params.score;
	var numErrors = req.params.numErrors;

	//determine winner
	var game = hangman_games[host];
	if(game.client == client){
		game.client_score = score;
		game.client_errors = numErrors;
	}else if(game.host == client){
		game.host_score = score;
		game.host_errors = numErrors;
	}

	if(game.client_score != null && game.host_score != null){
		//determine a winner
		if(game.client_score > game.host_score){
			game.winner = game.client;
		}else if(game.host_score > game.client_score){
			game.winner = game.host;
		}else{
			//scores are tied
			if(game.client_errors < game.host_errors){
				game.winner = game.client;
			}else if(game.host_errors < game.client_errors){
				game.winner = game.host;
			}else{
				//an actual tie
				game.winner = "tie";
			}
		}
	}
	
	res.send("No error");
	console.log(game);
});

//called at the end of the game until there is a winner
//returns a refreshed game object
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
	var wordsList = ["acres",
"adult",
"advice",
"arrangement",
"attempt",
"august",
"autumn",
"border",
"breeze",
"brick",
"calm",
"canal",
"casey",
"cast",
"chose",
"claws",
"coach",
"constantly",
"contrast",
"cookies",
"customs",
"damage",
"danny",
"deeply",
"depth",
"discussion",
"doll",
"donkey",
"egypt",
"ellen",
"essential",
"exchange",
"exist",
"explanation",
"facing",
"film",
"finest",
"fireplace",
"floating",
"folks",
"fort",
"garage",
"grabbed",
"grandmother",
"habit",
"happily",
"harry",
"heading",
"hunter",
"illinois",
"image",
"independent",
"instant",
"january",
"kids",
"label",
"lee",
"lungs",
"manufacturing",
"martin",
"mathematics",
"melted",
"memory",
"mill",
"mission",
"monkey",
"mount",
"mysterious",
"neighborhood",
"norway",
"nuts",
"occasionally",
"official",
"ourselves",
"palace",
"pennsylvania",
"philadelphia",
"plates",
"poetry",
"policeman",
"positive",
"possibly",
"practical",
"pride",
"promised",
"recall",
"relationship",
"remarkable",
"require",
"rhyme",
"rocky",
"rubbed",
"rush",
"sale",
"satellites",
"satisfied",
"scared",
"selection",
"shake",
"shaking",
"shallow",
"shout",
"silly",
"simplest",
"slight",
"slip",
"slope",
"soap",
"solar",
"species",
"spin",
"stiff",
"swung",
"tales",
"thumb",
"tobacco",
"toy",
"trap",
"treated",
"tune",
"university",
"vapor",
"vessels",
"wealth",
"wolf",
"zoo"]
	wordsList.sort( function() { return 0.5 - Math.random() } );
	return wordsList.slice(0, 20); //grab top 20 elements
}


//-------------END HANGMAN------------------
app.set("port", port);

app.listen(app.get("port"), function () {
  console.log("3_in_1_server listening on port " + ip.address() + ":" + port + "!")
})
