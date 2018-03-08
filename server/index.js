var mysql = require("mysql")
var express = require("express");
var app = express();
var port = process.env.PORT || 8080;

var pool  = mysql.createPool(process.env.JAWSDB_URL);

app.get("/", function (req, res) {
    res.send("Hello World!");
});


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