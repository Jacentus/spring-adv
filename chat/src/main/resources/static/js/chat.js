$(() => {

    let stompClient = null;

    function updateView(isConnected) {
        $('#username').prop('disabled', isConnected);
        $('#connectBtn').prop('disabled', isConnected);
        $('#disconnectBtn').prop('disabled', !isConnected);
        $('#sendBtn').prop('disabled', !isConnected);
        $('#message').prop('disabled', !isConnected);
        $('#recipient').prop('disabled', !isConnected);
        if (isConnected) {
            $('#messages').text('');
        }
    }

    function connect() {
        const user = getUser();
        const socket = new SockJS('/chat');
        stompClient = Stomp.over(socket);
        stompClient.connect({user}, onConnected);
    }

    function getUser() {
        return $('#username').val();
    }

    function onConnected() {
        stompClient.subscribe('/main-room', onMessage);
        updateView(true);
    }

    function disconnect() {
        stompClient.disconnect();
        updateView(false);
    }

    function onMessage(message) {
        const body = JSON.parse(message.body);
        $(`<p>${body.timestamp} ${body.sender}: ${body.text}</p>`).appendTo($('#messages'));
    }

    function sendMessage() {
        const message = {
          sender: getUser(),
          recipients: $('#recipient').val().split(','),
          text: $('#message').val()
        };
        stompClient.send('/ws/chat', {}, JSON.stringify(message));
    }

    updateView(false);
    $('#connectBtn').click(connect);
    $('#disconnectBtn').click(disconnect);
    $('#sendBtn').click(sendMessage);

});
