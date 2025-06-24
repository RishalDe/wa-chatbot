let currentUser = null;

// Load the conversation list every 5 seconds
setInterval(loadConversations, 5000);
window.onload = () => {
    loadConversations();
};

function loadConversations() {
    fetch('/messages/conversations')
        .then(res => res.json())
        .then(numbers => {
            const list = document.getElementById('conversationList');
            list.innerHTML = '<h2>Salty Tuco</h2>';
            numbers.forEach(number => {
                const div = document.createElement('div');
                div.className = 'contact';
                div.innerText = number;
                div.onclick = () => selectUser(number);
                list.appendChild(div);
            });
        });
}

function selectUser(user) {
    currentUser = user;
    document.getElementById('messageArea').innerHTML = '';
    loadMessages();
}

function loadMessages() {
    if (!currentUser) return;

    fetch(`/messages/conversation/${currentUser}`)
        .then(res => res.json())
        .then(data => {
            const container = document.getElementById('messageArea');
            container.innerHTML = '';
            data.forEach(msg => {
                const div = document.createElement('div');
                div.classList.add('message');
                div.classList.add(msg.from === 'BOT' ? 'from-me' : 'from-other');
                div.innerText = `${msg.from}: ${msg.text}`;
                container.appendChild(div);
            });
        });
}

function sendMessage() {
    const text = document.getElementById('messageInput').value;
    if (!text || !currentUser) return;

    const message = { from: 'BOT', to: currentUser, text: text };

    fetch('/messages', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(message)
    }).then(() => {
        document.getElementById('messageInput').value = '';
        loadMessages();
    });
}
