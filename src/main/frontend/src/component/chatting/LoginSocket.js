import React, { useEffect, useRef, createContext, useState } from 'react';

export const AppContext = createContext();

export function AppProvider({children}){

    const loginsocket = new WebSocket("ws://localhost:8080/intoHomePage");
    const [ loginList , setLoginList ] = useState([])


    return (
        <AppContext.Provider value={{ loginsocket,loginList }}>
          {children}
        </AppContext.Provider>
    );
}



/*
export default function LoginSocket(props) {
    const socketRef = null;
    useEffect(() => {
      if (!socketRef.current) {
            socketRef = new WebSocket("ws://localhost:8080/intoHomePage");
            socketRef.onopen = (e) => {
                console.log('로그인 서버 연결');
            };
            socketRef.onerror = (e) => {
                console.log('로그인 서버 에러');
            };
            socketRef.onmessage = (e) => {
                console.log('로그인 서버에서 메세지 전달');
                console.log(e.data);
            };
            socketRef.onclose = (e)=>{
                console.log('로그인 서버 닫힘')
            };
        }

    }, []);

    console.log(socketRef)

    return (
        <AppContext.Provider value={socketRef}>
          {props.children}
        </AppContext.Provider>
    );
}
*/