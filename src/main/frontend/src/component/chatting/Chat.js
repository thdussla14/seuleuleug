import React from 'react'
import {useEffect, useState, useRef} from 'react'
import Container from '@mui/material/Container';
import { useParams } from 'react-router-dom'; // HTTP 경로 상의 매개변수 호출 해주는 함수
import '../../css/Chat.css';

export default function Chat(props){
    let [socket, setSocket] = useState(null);
    let [messages, setMessages] = useState([]);


    console.log(sessionStorage.getItem('email'))

    const params = useParams();
    let chatRoomId = null;
    const clientSocket = useRef(null);

    useEffect(() => {
        if(!clientSocket.current){
            // 채팅방에 들어온 사람이 의사인지 일반 회원인지 구분하여 소켓에 전달하는 정보 구분
            if(sessionStorage.getItem('loginType')==="doctor"){
                chatRoomId = sessionStorage.getItem('email');
                console.log(chatRoomId);
                clientSocket.current = new WebSocket("ws://localhost:8080/chat/"+chatRoomId);
                clientSocket.current.onopen = (e)=>{  // 서버에 접속했을때
                    console.log('의사가 서버 접속했습니다');
                    console.log(clientSocket.current);
                    clientSocket.current.send(JSON.stringify({ type : "enter", who : "doctor" }));
                }
            }else if(sessionStorage.getItem('loginType')==="normal"){
                chatRoomId = params.chatRoomId;
                console.log(chatRoomId);
                clientSocket.current = new WebSocket("ws://localhost:8080/chat/"+chatRoomId);
                clientSocket.current.onopen = (e)=>{  // 서버에 접속했을때
                    console.log('일반회원이 서버 접속했습니다');
                    console.log(clientSocket.current);
                    clientSocket.current.send(JSON.stringify({  type : "enter", who : "normal"  }));
                }
            }
            clientSocket.current.onclose = (e)=>{
                console.log('서버 나갔습니다');
                console.log(clientSocket.current);
            }// 서버에서 나갔을때
            clientSocket.current.onerror = (e)=>{
                console.log('소켓 오류');
                console.log(clientSocket.current);
            } // 에러 발생 시
            clientSocket.current.onmessage = (e)=>{ // 서버에서 메세지가 왔을때
                    console.log('서버소켓으로부터 메세지 수신');
                    console.log(e.data)
                    let data =  JSON.parse(e.data)
                    console.log(data)
                    //msgContent.push(data); // 배열에 내용 추가
                    setMessages( (messages) => [ ...messages, data ] ); // 재 렌더링
                    console.log(messages);
            }
        }
    }, [chatRoomId]);

    useEffect(() => {
      console.log(messages);
    }, [messages]);


    function sendMessage(message) {
        console.log(message);
        console.log(typeof(message));
        let msg = {
            message : message,
            type : "msg",
            sender : sessionStorage.getItem('email')
        }
        const data = JSON.stringify(msg);
        console.log(data);
        clientSocket.current.send(data);
    }

    return (
      <Container className="container">
        {messages.map((message, index) => (
          <p
            key={index}
            className={`message ${message.sender === sessionStorage.getItem('email') ? 'sender' : 'receiver'}`}
          >
            {message.message}
          </p>
        ))}
        <div className="input-container">
          <input
            type="text"
            className="input"
            onKeyPress={(event) => {
              if (event.key === 'Enter') {
                sendMessage(event.target.value);
                event.target.value = '';
              }
            }}
          />
          <button className="input-button" onClick={() => sendMessage(document.querySelector('.input').value)}>
            전송
          </button>
        </div>
      </Container>
    );
}