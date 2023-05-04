import React from 'react';
import {useEffect, useState, useRef} from 'react'
import Container from '@mui/material/Container';
import axios from 'axios';

export default function SingUp(props){

    let memail = useRef(null);
    let mphone = useRef(null);

    const signup = ()=>{
        //console.log(memail.current.value);
        //console.log(mphone.current.value);
        let info = {
            memail : memail.current.value,
            mphone : mphone.current.value
        }
        axios.post("/member/singup", info ).then( r=>{
            console.log(r.data);
            if(r.data==true){
                alert('회원가입 성공');
                window.location.href="/";
            }else{
                alert('회원가입 실패');
            }
        })
    }

    return(<>
        <Container>
            <h5> 회원가입 페이지 </h5>
            <input ref={memail} className="memail" type="text" placeHolder="이메일" />
            <input ref={mphone} className="mphone" type="text" placeHolder="전화번호" />
            <button onClick={signup} type="button">회원가입</button>
        </Container>
    </>)
}