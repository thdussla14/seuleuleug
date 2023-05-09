import React from 'react'
import {useEffect, useState, useRef} from 'react'
import Container from '@mui/material/Container';
import { useParams } from 'react-router-dom'; // HTTP 경로 상의 매개변수 호출 해주는 함수

export default function Chat(props){
    const params = useParams();
    let [socket, setSocket] = useState(null);
    let [messages, setMessages] = useState([]);
    const clientSocket = useRef(null);
    let chatRoomId = params.chatRoomId
    console.log(chatRoomId);

    useEffect(() => {
        if(!clientSocket.current){
            clientSocket.current = new WebSocket("ws://localhost:8080/chat/"+chatRoomId);
            clientSocket.current.onopen = (e)=>{  // 서버에 접속했을때
                console.log('서버 접속했습니다');
                console.log(clientSocket.current);
                clientSocket.current.send(JSON.stringify({ chatRoomId: chatRoomId }));
            }
            clientSocket.current.onclose = (e)=>{ console.log('서버 나갔습니다'); }// 서버에서 나갔을때
            clientSocket.current.onerror = (e)=>{ console.log('소켓 오류'); } // 에러 발생 시
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
      const data = JSON.stringify({ message });
      console.log(data);
      clientSocket.current.send(data);
    }

    return (<>
        <Container>
          {messages.map((message, index) => (
            <p key={index}>{message.message}</p>
          ))}
          <input type="text" onKeyPress={event => {
            if (event.key === 'Enter') {
              sendMessage(event.target.value);
              event.target.value = '';
            }
          }} />
        </Container>
      </>);
}