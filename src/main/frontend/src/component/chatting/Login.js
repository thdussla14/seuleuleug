import React from 'react';
import {useEffect, useState, useRef, useContext} from 'react'
import Container from '@mui/material/Container';
import axios from 'axios';
import { AppContext } from './LoginSocket';


export default function Hlogin(props){
    let inputMemail = useRef(null);
    let inputMphone = useRef(null);

    const socketRef = useContext(AppContext);

    const mlogin = () => {
       let memail = inputMemail.current.value;
       let mphone = inputMphone.current.value;

        axios.get("/member/login", {params : { "memail" : memail , "mphone" : mphone }} ).then( r=>{
            console.log(r.data);
            if(r.data!==null){
                alert('로그인 성공');
                sessionStorage.setItem('email', memail);
                sessionStorage.setItem('loginType', "normal");

                console.log(socketRef)
                socketRef.current.send(JSON.stringify({
                    type : "login",
                    who : "normal",
                    userEmail : r.data.memail
                }));
                window.location.href="/";
            }else{
                alert('로그인 실패');
            }
        })
    }



    return(<>
        <Container>
            <h5> 일반 로그인 페이지 </h5>
                <input ref={inputMemail} className="hmemail" type="text" placeHolder="이메일" />
                <input ref={inputMphone} className="mphone" type="text" placeHolder="핸드폰" />
                <button onClick={mlogin} type="button">로그인</button>
        </Container>
    </>)


}