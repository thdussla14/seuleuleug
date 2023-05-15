import React from 'react';
import {useEffect, useState, useRef} from 'react'
import Container from '@mui/material/Container';
import axios from 'axios';

export default function Hlogin(props){

    let inputHmemail = useRef(null);
    let inputHpassword = useRef(null);

    const socketRef = props.socketRef;

    const hlogin = () => {
       let hmemail = inputHmemail.current.value;
       let hpassword = inputHpassword.current.value;

        axios.get("/hmember/hlogin", {params : { "hmemail" : hmemail , "hpassword" : hpassword }} ).then( r=>{
            console.log(r.data);
            if(r.data!==null){
                alert('로그인 성공');
                sessionStorage.setItem('email', hmemail);
                sessionStorage.setItem('loginType', "doctor");
                {/*socketRef.current.send(JSON.stringify({
                    type : "login",
                    who : "doctor",
                    userEmail : r.data.hmemail
                }));*/}
                window.location.href="/";
            }else{
                alert('로그인 실패');
            }
        })
    }



    return(<>
        <Container>
            <h5> 의사 로그인 페이지 </h5>
                <input ref={inputHmemail} className="hmemail" type="text" placeHolder="이메일" />
                <input ref={inputHpassword} className="hmemail" type="text" placeHolder="비밀번호" />
                <button onClick={hlogin} type="button">로그인</button>
        </Container>
    </>)


}