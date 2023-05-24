import React from 'react'
import {useEffect, useState, useRef, useContext} from 'react'
import axios from 'axios';
import Avatar from "@mui/material/Avatar";
import Container from '@mui/material/Container';
import { useParams  } from 'react-router-dom'; // HTTP 경로 상의 매개변수 호출 해주는 함수
import '../../css/Chat.css';
import { WebSocketContext } from './WebSocketContext';
import { useNavigate } from "react-router-dom";

export default function Chat(props){
    let [messages, setMessages] = useState([]);
    console.log(sessionStorage.getItem('email'))
    const navigate = useNavigate();
    const [loginSocket, setLoginSocket] = useState(useContext(WebSocketContext));
    //const websocket = useContext(WebSocketContext); //useContext에서 로그인 소켓 얻기
    /*
    useEffect( ()=>{
        setLoginSocket(websocket);
    })*/
    //setLoginSocket(useContext(WebSocketContext));


    const handleClick = () => {
        clientSocket.current.close();
        navigate('/');
    };
    /*
    const sendMessageToLoginSocket = (msg)=>{
        console.log(loginSocket);
        console.log(msg)
        let text = "{ type : " + `${msg}`+  " }" ;
        loginSocket.send(JSON.stringify(text));
        loginSocket.send(JSON.stringify({ type : "inRoom" }));
    }
    */
    const params = useParams();
    let chatRoomId = null;
    const [doctorInfo, setDoctorInfo] = useState(null);
    const clientSocket = useRef(null);



    useEffect(() => {
        console.log(clientSocket.current)
        if(!clientSocket.current){
            // 채팅방에 들어온 사람이 의사인지 일반 회원인지 구분하여 소켓에 전달하는 정보 구분
            if(sessionStorage.getItem('loginType')==="doctor"){
                chatRoomId = sessionStorage.getItem('email');
                clientSocket.current = new WebSocket("ws://http://ec2-13-209-3-7.ap-northeast-2.compute.amazonaws.com/:8080/chat/"+chatRoomId);
                clientSocket.current.onopen = async (e)=>{  // 서버에 접속했을때
                    console.log('의사가 서버 접속했습니다');
                    console.log(clientSocket.current);
                    clientSocket.current.send(JSON.stringify({ type : "enter", who : "doctor" }));
                    const doctorData = await getDoctorInfo(chatRoomId); // 의사 정보 검색
                    setDoctorInfo(doctorData);
                    loginSocket.send(JSON.stringify({ type : "inRoom" }));
                }
            }else if(sessionStorage.getItem('loginType')==="normal"){
                chatRoomId = params.chatRoomId;
                clientSocket.current = new WebSocket("ws://http://ec2-13-209-3-7.ap-northeast-2.compute.amazonaws.com/:8080/chat/"+chatRoomId);
                clientSocket.current.onopen = async (e)=>{  // 서버에 접속했을때
                    console.log('일반회원이 서버 접속했습니다');
                    console.log(clientSocket.current);
                    clientSocket.current.send(JSON.stringify({  type : "enter", who : "normal"  }));
                    const doctorData = await getDoctorInfo(chatRoomId); // 의사 정보 검색
                    setDoctorInfo(doctorData);
                    loginSocket.send(JSON.stringify({ type : "inRoom" }));
                }
            }
            clientSocket.current.onclose = (e)=>{
                console.log('서버 나갔습니다');
                loginSocket.send(JSON.stringify({ type : "outRoom" }));
                console.log(clientSocket.current);
            }// 서버에서 나갔을때
            clientSocket.current.onerror = (e)=>{
                console.log('소켓 오류');
                console.log(clientSocket.current);
            } // 에러 발생 시
            clientSocket.current.onmessage = (e)=>{ // 서버에서 메세지가 왔을때
                    console.log('서버소켓으로부터 메세지 수신');
                    let data =  JSON.parse(e.data)
                    setMessages( (messages) => [ ...messages, data ] ); // 재 렌더링
            }
        }
    }, [chatRoomId]);

    useEffect(() => {
      console.log(messages);
    }, [messages]);


    function sendMessage(message) {
        let msg = {
            message : message,
            type : "msg",
            sender : sessionStorage.getItem('email')
        }
        const data = JSON.stringify(msg);
        clientSocket.current.send(data);
        document.querySelector('.input').value = ''
    }

    const getDoctorInfo = async (chatRoomId) => {
        try {
          const response = await axios.get("/hmember/hcomment", { params: { hmemail: chatRoomId } });
          return response.data;
        } catch (error) {
          console.error(error);
          return null;
        }
      };

    return (<>
        {doctorInfo &&
            <div className="doctorInfoBox">
                <Avatar alt="Remy Sharp" src={doctorInfo.hmpimg} />
                <div className="doctorName"> {doctorInfo.hmname} 선생님 </div>
                <div className="hospital"> 소속병원 : {doctorInfo.hname} </div>
            </div>
        }
      <Container className="container">

        <button onClick={handleClick} >나가기</button>
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
              }
            }}
          />
          <button className="input-button" onClick={() => sendMessage(document.querySelector('.input').value)}>
            전송
          </button>
        </div>
      </Container>
    </>);
}