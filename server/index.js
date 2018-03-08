//var mysql = require("mysql");
var express = require("express");
var app = express();
var port = process.env.PORT || 8080;

//var pool  = mysql.createPool(process.env.JAWSDB_URL);

var connect_4_count = 1;
var connect_4_games = {};

function checkPlayer(user, game) {
	if (game.host == user || game.client == user) {
		return true;
	}
	return false;
}

app.get("/", function (req, res) {
    res.send("Hello World!");
});

app.get("/connect_4_host/:host", function (req, res) {
	var host = req.params.host
	console.log("New Connect 4 ID: " + connect_4_count);
	res.send(connect_4_count.toString());
	game = {
		gameID: connect_4_count, 
		host: host,
		state: "START"
		};
	connect_4_games[connect_4_count] = game
	connect_4_count++;
});

app.get("/connect_4_join/:gameID/:client", function (req, res) {
	var gameID = req.params.gameID;
	var client = req.params.client;
	console.log("Connect 4 ID: " + gameID);
	console.log("Being joined by " + client);
	if (gameID in connect_4_games) {	
		var game = connect_4_games[gameID];
		res.send("Game joined!");
		game.client = client;
		console.log(game);
	}
	else {
		console.log("Error: gameID not found");
		res.send("Error: gameID not found")
	}
});

app.route("/connect_4/:gameID(\\d+)/:user")
	.get(function (req, res) {
		var gameID = req.params.gameID;
		var user = req.params.user;
		if (gameID in connect_4_games) {
			var game = connect_4_games[gameID];
			if (checkPlayer(user, game)) {
				console.log(connect_4_games[req.params.gameID]);
				res.json(connect_4_games[req.params.gameID]);
			}
			else {
				console.log("Player not found in game: " + gameID);
				res.send("Player not found in game: " + gameID);
			}
		}
		else {
			console.log("Error: gameID not found");
			res.send("Error: gameID not found")
		}		
	})
	.post(function (req, res) {
		var gameID = req.params.gameID;
		if (gameID in connect_4_games) {
			console.log(connect_4_games[req.params.gameID]);
			res.json(connect_4_games[req.params.gameID]);
		}
		else {
			console.log("Error: gameID not found");
			res.send("Error: gameID not found")
		}	
	})
	
app.get("/retrieve", function (req, res) {
    pool.getConnection(function(error, con) {
        if (error) throw error;
		var query = "SELECT (SELECT COUNT(*) FROM qdcise2a2swf0oxi.simpleapp WHERE Operation = 'Increment') - (SELECT COUNT(*) FROM qdcise2a2swf0oxi.simpleapp WHERE Operation = 'Decrement') as Sum;";
		con.query(query, function (err, result) {
			con.release();
			if (!err) {
				console.log("Retrieved");
				res.send(result);
			}
			else {
				throw err;
			}
        });
    });
});


app.route("/increment")
	.get(function (req, res) {
		pool.getConnection(function(error, con) {
			if (error) throw error;
			var query = "SELECT COUNT(*) as Num FROM qdcise2a2swf0oxi.simpleapp WHERE Operation = 'Increment';";
			con.query(query, function (err, result) {
				con.release();
				if (!err) {
					console.log("get Increment");
					res.send(result);
				}
				else {
					throw err;
				}
			});
		});
	})
	.post(function (req, res) {
		pool.getConnection(function(error, con) {
			if (error) throw error;
			var query = "INSERT INTO qdcise2a2swf0oxi.simpleapp (Operation) VALUES ('Increment');";
			con.query(query, function (err, result) {
				con.release();
				if (!err) {
					console.log("Incremented");
					res.send("Incremented");
				}
				else {
					throw err;
				}
			});
		});
	})


app.route("/decrement")
	.get(function (req, res) {
		pool.getConnection(function(error, con) {
			if (error) throw error;
			var query = "SELECT COUNT(*) as Num FROM qdcise2a2swf0oxi.simpleapp WHERE Operation = 'Decrement';";
			con.query(query, function (err, result) {
				con.release();
				if (!err) {
					console.log("get Decrement");
					res.send(result);
				}
				else {
					throw err;
				}
			});
		});
	})
	.post(function (req, res) {
		pool.getConnection(function(error, con) {
			if (error) throw error;
			var query = "INSERT INTO qdcise2a2swf0oxi.simpleapp (Operation) VALUES ('Decrement');";
			con.query(query, function (err, result) {
				con.release();
				if (!err) {
					console.log("Decremented");
					res.send("Decremented");
				}
				else {
					throw err;
				}
			});
		});
	})


app.set("port", port);

app.listen(app.get("port"), function () {
  console.log("3_in_1_server listening on port " + port + "!")
})