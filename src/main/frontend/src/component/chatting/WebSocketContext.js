import React, { createContext, useState, useEffect } from 'react';


const WebSocketContext = createContext();

const WebSocketProvider = ({ children }) => {
  const [websocket, setWebSocket] = useState(null);

  useEffect(() => {
    if(websocket==null){
        const email = sessionStorage.getItem("email");
        const loginType = sessionStorage.getItem("loginType");
        const newWebSocket = new WebSocket("ws://localhost:8080/intoHomePage/" + email);
        newWebSocket.onopen = () => {
          newWebSocket.send(JSON.stringify({ type: "enter", loginType: loginType }));
        };
        if(loginType==="doctor"){
            console.log('의사 로그인 소켓 접속')
            newWebSocket.onmessage = (e) => {
                console.log('의사에게 메세지 전달 : ' + e)
                console.log(e)
                let message = JSON.parse(e.data);
                let getNormal = message.normal
                console.log(message);
                console.log(message.normal);
                let answer = window.confirm(getNormal+'님에게서 상담 신청이 왔습니다.')
                newWebSocket.send(JSON.stringify({
                    type : "answer" ,
                    answer: answer?"true":"false" ,
                    normal : getNormal,
                    doctor : sessionStorage.getItem("email")
                    }))
                if(answer){
                    window.location.href="/chatting/"+sessionStorage.getItem("email");
                }
            };
        }else if(loginType==="normal"){
            console.log('일반 로그인 소켓 접속')
            newWebSocket.onmessage = (e) => {
                console.log('일반회원에게 메세지 전달 : ' + e)
                console.log(e)
                let message = JSON.parse(e.data);
                let doctor = message.doctor;
                console.log(message);
                console.log(message.doctor);
                console.log(message.answer);
                if(message.answer==="true"){
                    let answer = window.confirm('상담을 시작합니다.');
                    if(answer){
                        window.location.href="/chatting/"+doctor;
                    }
                }
            };
        }

        setWebSocket(newWebSocket);
        return () => {
          newWebSocket.close();
        };
    }
  }, []);

  return (
    <WebSocketContext.Provider value={websocket}>
      {children}
    </WebSocketContext.Provider>
  );
};

export { WebSocketContext, WebSocketProvider };