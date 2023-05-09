import React from 'react'
import {useEffect, useState, useRef} from 'react'
import Container from '@mui/material/Container';
import { useSearchParams } from 'react-router-dom';

export default function Chat(props){

    let [socket, setSocket] = useState(null);
    let [messages, setMessages] = useState([]);
    let clientSocket = useRef(null);
    const [searchParams, setSearchParams] = useSearchParams();
    let chatRoomId = searchParams.get("chatRoomId")

    useEffect(() => {
        if(!clientSocket.current){
            clientSocket = new WebSocket("ws://localhost:8080/chat/"+chatRoomId);
        }
    }, [chatRoomId]);

    function sendMessage(message) {
      const data = JSON.stringify({ message });
      console.log(data);
      clientSocket.current.send(data);
    }

    return (
        <Container>
          {messages.map((message, index) => (
            <p key={index}>{message}</p>
          ))}
          <input type="text" onKeyPress={event => {
            if (event.key === 'Enter') {
              sendMessage(event.target.value);
              event.target.value = '';
            }
          }} />
        </Container>
      );
}