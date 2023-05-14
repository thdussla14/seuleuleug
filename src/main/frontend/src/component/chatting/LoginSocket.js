import React, { useEffect, useRef, createContext, useState } from 'react';

export const AppContext = createContext();

export default function LoginSocket(props) {
  const [webSocket, setWebSocket] = useState(null);
  const socketRef = useRef(null);

  useEffect(() => {
    if (!socketRef.current) {
      socketRef.current = new WebSocket("ws://localhost:8080/intoHomePage");
      socketRef.current.onopen = (e) => {
        console.log('로그인 서버 연결');
      };
      socketRef.current.onerror = (e) => {
        console.log('로그인 서버 에러');
      };
      socketRef.current.onmessage = (e) => {
        console.log('로그인 서버에서 메세지 전달');
        console.log(e.data);
      };

      setWebSocket(socketRef.current);
    }

    return () => {
      socketRef.current.onclose = null;
    };
  }, []);

  return (
    <AppContext.Provider value={webSocket}>
      {props.children}
    </AppContext.Provider>
  );
}
