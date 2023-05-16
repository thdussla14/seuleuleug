import React, { useEffect, useRef, createContext, useState } from 'react';

const

export const LoginContext = createContext(
    {LoginListProvider}
);

export function LoginListProvider({children}){
    const [loginsocket,setLoginSocket] = useState(null);
    /*const handleWebSocket = (socket)=>{
        setLoginSocket(socket);
    }*/


    return (
        <LoginContext.Provider value={{loginsocket}}>
          {children}
        </LoginContext.Provider>
    );
}