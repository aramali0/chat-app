'use strict';

const roomslist = document.querySelector('#users');
const messageForm = document.querySelector('#messageForm');
const chatMessages = document.querySelector('#chat-messages');
const chatArea = document.querySelector('.chat-area');

let stompClient;

function addUser(event){
event.preventDefault();
const username = document.querySelector('#identifier').value;
const pass = document.querySelector('#password').value;
const nom = document.querySelector('#nom').value;
const prenom = document.querySelector('#prenom').value;
const age = document.querySelector('#age').value;
const user = {"identifier":username,pass,nom,prenom,age}
fetchSave(user,"/user");
window.location.href = 'index.html';
}

async function fetchSave(item,lien) {
   const response = await fetch("http://localhost:8080/v1/api"+lien, {
      method: 'POST',
      headers: {
         'Content-Type': 'application/json',
      },
      body: JSON.stringify(item),
   });
}

async function fetchData(endpoint){
    try {
        const response = await fetch("http://localhost:8080/v1/api"+endpoint);

        if (!response.ok) {
            throw new Error(`HTTP error! Status: ${response.status}`);
        }
        return await response.json();
    } catch (error) {
        console.error("Fetch error:", error);
        throw error;
    }
}

function login(event) {
    event.preventDefault();
    const username = document.querySelector("#username").value;
    const password = document.querySelector("#password").value;
    const warning1 = document.querySelector("#warning1");
    const warning2 = document.querySelector("#warning2");

    verified1(username, password).then(isValid => {
        if (!isValid) {
            warning1.hidden = false;
        } else {
            verified2(username).then(isBanned =>
            {
                console.log(isBanned);
                if(isBanned)
                {

                    warning2.hidden = false;
                }
                else{
                     localStorage.setItem("Identifier", username);
                     addLog(username,"login")
                        setTimeout(() => {
                               window.location.href = 'chat.html';
                        }, 100);

                }
            });
        }
    });

}

async function addLog(username,type)
{
    const user = await fetchData(`/user/${username}`);
    const log = {"user":user,"timestamp":new Date(),"type": type}
    console.log(log);
    fetchSave(log,"/log")
}

async function verified1(username, pass) {
    try {
        const list = await fetchData("/user");

        const user = list.find(item => item.username === username && item.password === pass);

        return user !== undefined;
    } catch (error) {
        console.error("Error fetching user data:", error);
        return false;
    }
}

async function verified2(username) {
        const user = await fetchData(`/user/${username}`);
        return user.banned;
}

function utilisation(){
    const identifier = localStorage.getItem("Identifier");
    const currentURL = window.location.href;
   if (identifier && currentURL.indexOf('chat.html') === -1){
           window.location.replace('chat.html');
   } else if (!identifier && currentURL.indexOf('index.html') === -1){
           window.location.replace('index.html');
   }
}



function logout() {
    const identifier = localStorage.getItem("Identifier");
    addLog(identifier,"logout")
     setTimeout(() => {
       localStorage.removeItem("Identifier");
          window.location.href = 'index.html';
    }, 100);


}

function saveName() {
    const identifier = localStorage.getItem('Identifier');
    if (identifier != null){
        document.getElementById("identifier").innerText = identifier;
    }
}

getUsers();
 async function getUsers() {
    var identifier = localStorage.getItem("Identifier");
    if(identifier)
    {
        try {
            const users = await fetchData(`/user`);
            console.log(users);
            const usersNotMe = users.filter(user => user.username !== identifier);
            usersNotMe.forEach((item) => {
                appendRoomElement(item, roomslist);
            });
                 initializeChat();
                 saveName();
                 getMessages();
        } catch (error) {
            console.error('Error fetching user data:', error);
        }
    }
}

async function appendRoomElement(user, roomslist) {
    const listItem = document.createElement('li');

    listItem.classList.add('user-item');
    listItem.id = user.username;

    const username = document.createElement('span');
    username.textContent = user.username;

    const status = document.createElement('span');
    status.textContent = '';
    status.classList.add('nbr-msg');
    const banne = document.createElement('span');
    banne.textContent = '';
    banne.classList.add('nbr-msg');
    banne.classList.add('hidden');
    banne.classList.add('yellow');

    const log = await fetchData(`/logs/${user.id}`)
    const taill = log.length;
    if(taill == 0){
        status.classList.add('red');
    }
    else{
        const last_log = log[taill - 1];
        if(last_log.type == "login")
        {
            status.classList.add('green');
        }
        else{
            status.classList.add('red');
        }
    }
    if(user.banned)
    {
            banne.classList.remove('hidden');
    }

    listItem.appendChild(username);
    listItem.appendChild(status);
    listItem.appendChild(banne);

    listItem.addEventListener('click', userItemClick);
    roomslist.appendChild(listItem);
}

async function userItemClick(event) {
    const clickedUser = event.currentTarget;
    const username = clickedUser.getAttribute('id');
     localStorage.setItem("selectedUser", username);
   var identifier = localStorage.getItem("Identifier");
   const userSelected = await fetchData(`/user/${username}`)
   const currentUser = await fetchData(`/user/${identifier}`)
   if(currentUser.permession === "moderateur" || currentUser.permession === "administrateur")
   {
        openPopup();
        if(currentUser.permession === "administrateur"){
            document.querySelector("#selection").hidden = false;
        }
   }
}

async function banned()
{
    const banned = localStorage.getItem("selectedUser");
    console.log(banned);
    const isbanned = await fetchData(`/user/banned/${banned}`)
    closePopup()
}

async function changePermition()
{

    var selectedOptions = document.getElementById('usersList').selectedOptions;
     const selectedUser = localStorage.getItem("selectedUser");

    console.log(selectedOptions[0].value);

     if(selectedOptions[0].value === "administrateur")
     {
        const user = await fetchData(`/user/${selectedUser}/1`)
     }
     else if(selectedOptions[0].value === "moderateur"){
        const user = await fetchData(`/user/${selectedUser}/2`)
     }
     else{
        const user = await fetchData(`/user/${selectedUser}/3`)
     }
    closePopup()
}


async function openPopup() {
        document.getElementById('addRoomPopup').style.display = 'block';

    }

function closePopup() {
        document.getElementById('addRoomPopup').style.display = 'none';
}

localStorage.setItem("WebSocketURL", "/ws");

function initializeChat() {
    const socket = new SockJS(localStorage.getItem("WebSocketURL"));
    stompClient = Stomp.over(socket);
    stompClient.connect({}, () => onConnected(stompClient), onError);
}

async function onConnected(stompClient) {
        stompClient.subscribe("/user/1/queue/messages", onMessageReceived);
        stompClient.subscribe("/user/public", onMessageReceived);
}


function onError() {
    console.error("Error connecting to WebSocket server");
}


async function sendMessage(event) {
    event.preventDefault();
    const senderId = localStorage.getItem("Identifier");
    const messageInput = document.querySelector("#message");
    const sender = await fetchData(`/user/${senderId}`)
    const time = new Date();
    const message = {"user_id": sender,  "content": messageInput.value, "timestamp": time};
    if (!stompClient || !stompClient.connected) {
        stompClient = Stomp.over(new SockJS(localStorage.getItem("WebSocketURL")));
        stompClient.connect({}, () => {
            stompClient.send("/app/chat", {}, JSON.stringify(message));
            //displayMessage(sender.identifier, messageInput.value);
            messageInput.value = '';
        }, onError);
    } else {
        stompClient.send("/app/chat", {}, JSON.stringify(message));
        //displayMessage(sender.username, messageInput.value);
        messageInput.value = '';
    }
}

async function onMessageReceived(payload) {
    try
    {
        const message = JSON.parse(payload.body);
        console.log(message);
        var locaidetifier = localStorage.getItem("identifier");
        if (locaidetifier != message.user_id.username){
            displayMessage(message);
        }
    } catch (error) {
             console.error('Error in onMessageReceived:', error);
    }
}

function displayMessage(message) {
    const messageContainer = document.createElement('div');
    messageContainer.classList.add('message');
    var identifier = localStorage.getItem("Identifier");

    if (message.user_id.username === identifier) {
        messageContainer.classList.add('sender');
    } else {
        messageContainer.classList.add('receiver');
    }

    const messageContent = document.createElement('div');

    const username = document.createElement('p');
    username.textContent = message.user_id.username;
    username.classList.add('username');
    messageContent.appendChild(username);

    const content = document.createElement('p');
    content.textContent = message.content;
    messageContent.appendChild(content);

    const date = document.createElement('p');
    const messageDate = new Date(message.timestamp);
    date.textContent = formatMessageDate(messageDate) ;
    date.classList.add('date');
    messageContent.appendChild(date);

    messageContainer.appendChild(messageContent);
    chatMessages.appendChild(messageContainer);
}

function formatMessageDate(date) {

    return `${date.toLocaleTimeString()}`;
}

async function getMessages()
{
    const messageslist = await fetchData(`/message`);
    messageslist.forEach(item =>{
        displayMessage(item);
    })
}

document.addEventListener('DOMContentLoaded', function() {
    setTimeout(utilisation, 10);
});
