import React, {useState, useEffect} from 'react';
import axios from 'axios';
import Container from '@mui/material/Container';
import styles from '../css/main.css';

export default function Header(props) {

    console.log(sessionStorage)


    if(sessionStorage.length<=0){
        console.log(sessionStorage)
        console.log('세션스토리지 비어있음')
        sessionStorage.setItem('email', null);
        sessionStorage.setItem('loginType', null);
    }

    const logOut = () => {
        sessionStorage.setItem('email', null);
        sessionStorage.setItem('loginType', null);
        axios.get("/member/logout");
        window.location.href = '/';
    };

    return (
    <Container>
        <div className="header" >
            <a href="/" > Home </a>
            <a href="/admin/dashboard" > 관리자 </a>
            <a href="/hospital/hospitallist" > Hospital </a>
            <a href="/government/info" > 정부지원 </a>
            <a href="/board/boardlist" > 고민글 보기 </a>
            <a href="/challenge/challenge" > Challenge </a>


        {sessionStorage.getItem('email') !== 'null' ? (
            <button type="button" onClick={logOut}>
                로그아웃
            </button>
        ) : (
            <div>
                <a href="/login">일반회원 로그인</a>
                <a href="/signup">일반회원 회원가입</a>
                <a href="/hlogin">의사회원 로그인</a>
                <a href="/hsignup">의사회원 회원가입</a>
            </div>
        )}
            <a href="/chattinglist">채팅가능 리스트</a>
        </div>
    </Container>
    );
}