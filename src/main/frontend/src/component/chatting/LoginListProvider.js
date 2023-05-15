import React, { useEffect, useRef, createContext, useState } from 'react';

export const LoginContext = createContext();

export function LoginListProvider({children}){

    const loginsocket = new WebSocket("ws://localhost:8080/intoHomePage");
    loginsocket.onopen = (e)=>{
        console.log('로그인 서버 연결');
    }


    return (
        <LoginContext.Provider value={{ loginsocket }}>
          {children}
        </LoginContext.Provider>
    );
}