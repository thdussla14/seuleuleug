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
                console.log(message);
                console.log(message.normal);
            };
        }else if(loginType==="normal"){
            console.log('일반 로그인 소켓 접속')
            newWebSocket.onmessage = (e) => {
                console.log('일반회원에게 메세지 전달 : ' + e)
                console.log(e)
                let message = JSON.parse(e.data);
                console.log(message);
                console.log(message.normal);
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