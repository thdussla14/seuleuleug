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