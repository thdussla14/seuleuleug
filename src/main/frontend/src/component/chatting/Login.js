import React from 'react';
import {useEffect, useState, useRef, useContext} from 'react'
import Container from '@mui/material/Container';
import axios from 'axios';


export default function Hlogin(props){
    let inputMemail = useRef(null);
    let inputMphone = useRef(null);

    const mlogin = () => {
        let loginForm = document.querySelectorAll(".loginForm")[0];
        let loginFormData = new FormData(loginForm);
         console.log(loginFormData);
        axios.post("/member/login", loginFormData ).then( r=>{
            console.log(r.data);
            if(r.data != false){
                alert('로그인 성공');
                sessionStorage.setItem('email', r.data.memail);
                sessionStorage.setItem('loginType', "normal");
                window.location.href="/";
            }else{
                alert('로그인 실패');
            }
        })
    }



    return(<>
        <Container>
            <h3> 로그인 페이지 </h3>
            <form className="loginForm">
                <input ref={inputMemail} name="memail" type="text" placeHolder="이메일" />
                <input ref={inputMphone} name="mphone" type="text" placeHolder="핸드폰" />
                <button onClick={mlogin} type="button">로그인</button>
                <a href="/oauth2/authorization/kakao"> 카카오 로그인 </a>
            </form>
        </Container>
    </>)


}