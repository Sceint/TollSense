var app = express();
var server = require('http').createServer(app).listen(1337);
var io = require('socket.io').listen(server);

var Connection = require('tedious').Connection;
var Request = require('tedious').Request;
var express = require('express');

var config = {
    userName: 'gethadmin@tollsense.database.windows.net', // update me
    password: 'ArVeNi-181164', // update me
    server: 'tollsense.database.windows.net', // update me
    options: {
        database: 'TollSense', //update me
        encrypt: true
    }
}
var connection = new Connection(config);
// Attempt to connect and execute queries if connection goes through
connection.on('connect', function(err) {
    if (err) {
        console.log(err)
    } else {
        io.sockets.on('connection', function(client) {
            client.on('message', function(msg) {
                console.log(msg);
                // var str=;
                queryDatabase(msg.split(","));
            });
        });
    }
});


function queryDatabase(str) {
    console.log('Reading rows from the Table... ' + str);

    // Read all rows from table
    request = new Request(
        "insert into toll_logs(UNAME,VIN) values('" + str[0] + "','" + str[1] + "'');",
        function(err, rowCount, rows) {
            console.log(rowCount + ' row(s) inserted');
        }
    );
    connection.execSql(request);
}


app.post('/', function(req, res) {
    var fs = require('fs');
    fs.readfile('./index.html', function(err, html) {
        if (err) {
            throw err;
        }
        res.writeHeader(200, { "Content-Type": "text/html" });
        res.write(html);
        //res.end();
    });

    print_table(res);
});

//var s1;

function print_table(res1) {
    //console.log('Reading rows from the Table... '+str);

    // Read all rows from table
    request = new Request(
        "select * from toll_logs;",
        request.on('row', function(columns) {
            columns.forEach(function(column) {
                console.log("%s\t%s", column.metadata.colName, column.value);
            });
        });

        connection.execSql(request);
    }



    var counter = 0;

    function printonscreen() {

        io.sockets.on('connection', function(socket) {
            socket.emit('news', { news: '' });
            socket.on('echo', function(data) {
                var q = "SELECT TOP 1 * FROM toll_logs ORDER BY VIN DESC";
                console.log(data.back);
                socket.emit('news', { news: data.back });
            });
        });
    }