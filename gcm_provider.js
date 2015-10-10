var gcm = require('node-gcm');
var fs = require('fs');

var message = new gcm.Message();

var message = new gcm.Message({
    collapseKey: 'demo',
    delayWhileIdle: true,
    timeToLive: 3,
    data: {
        title: 'saltfactory GCM demo',
        message: 'Google Cloud Messaging Å×½ºÆ®',
        custom_key1: 'custom data1',
        custom_key2: 'custom data2'
    }
});

var server_api_key = 'AIzaSyDwipCJy_lTDppme9GJNupB161sEe_x8CM';
var sender = new gcm.Sender(server_api_key);
var registrationIds = [];

var token = 'd4AzfV_a49o:APA91bHgka-_xcCLTcq5zcvQ3RRZGo2yd1wAesVcalqFEIqz66AbcDUmNJiNjP29k53zqe7GUZy4BAbrZfRckpmehTliAmYeAjrToOKMwC9nOfIWdvdANFQnuvAjfV7lj3ooAEoAG0Yv';
registrationIds.push(token);

sender.send(message, registrationIds, 4, function (err, result) {
    console.log(result);
});