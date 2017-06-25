var Connection = require('tedious').Connection;
var Request = require('tedious').Request;

var config = {
  userName: 'gethadmin@tollsense.database.windows.net', // update me
  password: 'ArVeNi-181164', // update me
  server: 'tollsense.database.windows.net', // update me
  options: {
      database: 'TollSense' ,//update me
      encrypt : true
  }
}
var connection = new Connection(config);
// Attempt to connect and execute queries if connection goes through
connection.on('connect', function(err) {
    if (err) {
        console.log(err)
    }
    else {
		var express = require('express');
		var app = express();
		var server = require('http').createServer(app).listen(1337);
		var io = require('socket.io').listen(server);
		io.sockets.on('connection', function(client){
			client.on('message', function(msg){
				console.log(msg);
				// var str=;
				queryDatabase(msg.split(","));
			});
		});
	}
});




function queryDatabase(str){
    console.log('Reading rows from the Table... '+str);

    // Read all rows from table
    request = new Request(
        "insert into toll_logs(UNAME,VIN) values('"+str[0]+"','"+str[1]+"');",
        function(err, rowCount, rows) {
            console.log(rowCount + ' row(s) inserted');
        }
    );
    connection.execSql(request);
}
//         function(err, rowCount, rows) {
//             console.log(rowCount + ' row(s) returned');
//         }
//     );

//     request.on('row', function(columns) {
//         columns.forEach(function(column) {
//             console.log("%s\t%s", column.metadata.colName, column.value);
//         });
//     });

//     connection.execSql(request);
// }










// var counter=0;
// app.listen(8124);

// function handler (req, res) {
//     fs.readFile(__dirname + '/index.html',
//     function (err, data) {
//         if (err) {
//             res.writeHead(500);
//             return res.end('Error loading index.html');
//         }
//         printonscreen();
//         counter = 1;
//         res.writeHead(200);
//         res.end(data);
//     });
// }
// function printonscreen () {

// io.sockets.on('connection', function (socket) {
//     socket.emit('news', { news: '' });
//     socket.on('echo', function (data) {
//         while (counter <= 50) {
//             counter++;
//             console.log(data.back);
//             socket.emit('news', {news: data.back});
//         }
//     });
// });
// }