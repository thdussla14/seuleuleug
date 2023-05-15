import React, { useEffect, useRef, createContext, useState } from 'react';

export const LoginContext = createContext();

export function LoginListProvider({children}){
    const [loginsocket,setLoginSocket] = useState(null);
    const handleWebSocket = (socket)=>{
        setLoginSocket(socket);
    }


    return (
        <LoginContext.Provider value={{loginsocket, handleWebSocket}}>
          {children}
        </LoginContext.Provider>
    );
}