process.stdin.setEncoding('utf-8')
const io = require('socket.io').listen(3000);
const m = parseInt(process.argv[2]) || 3
const windowSize = 2 ** (m - 1)

let frameToSend, temporaryFrame, seqFirst = 0, seqN = 0
let store = {}
io.on('connection', function (socket) {
    console.log('connected:', socket.client.id);
    socket.on('serverEvent', function (data) {
        // TODO: 
    });

    process.stdin.resume()
    process.stdin.on('data', (d) => {
        if(seqN - seqFirst < windowSize) {
            frameToSend = makeFrame(getDataFromNetworkLayer())
            frameToSend += seqN.toString(2)
            storeFrame(seqN, frameToSend)
            // send frame
            socket.emit('clientEvent', frameToSend)
            // TODO: start timer

            seqN ++
        }
    })
});


function getDataFromNetworkLayer() {
    return Math.floor(Math.random() * (10 ** 10))
}

function makeFrame(s) {
    return s.toString(2)
}

function storeFrame(n ,frameToSend){
    store[n] = frameToSend
}
