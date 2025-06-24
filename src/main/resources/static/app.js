let currentUser = 'Alice';

function selectUser(user) {
    currentUser = user;
    document.getElementById('messageArea').innerHTML = '';
    loadMessages();
}

function loadMessages() {
    fetch('/messages')
        .then(res => res.json())
        .then(data => {
            const container = document.getElementById('messageArea');
            container.innerHTML = '';
            data.forEach(msg => {
                if (msg.to === currentUser || msg.from === currentUser) {
                    const div = document.createElement('div');
                    div.classList.add('message');
                    div.classList.add(msg.from === 'Me' ? 'from-me' : 'from-other');
                    div.innerText = `${msg.from}: ${msg.text}`;
                    container.appendChild(div);
                }
            });
        });
}

function sendMessage() {
    const text = document.getElementById('messageInput').value;
    const message = { from: 'Me', to: currentUser, text: text };

    fetch('/messages', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(message)
    }).then(() => {
        document.getElementById('messageInput').value = '';
        loadMessages();
    });
}

window.onload = () => {
    loadMessages();
};
