import React from 'react';
import {useEffect, useState, useRef, useContext} from 'react'
import Container from '@mui/material/Container';
import axios from 'axios';
import TextField from '@mui/material/TextField';
import Button from '@mui/material/Button';
import Box from '@mui/material/Box';
import Tab from '@mui/material/Tab';
import TabContext from '@mui/lab/TabContext';
import TabList from '@mui/lab/TabList';
import TabPanel from '@mui/lab/TabPanel';
import Stack from '@mui/material/Stack';
//import { LoginContext,LoginListProvider } from './LoginListProvider';
/*
const createWebSocket =() =>{
    const websocket = new WebSocket("ws://localhost:8080/intoHomePage");
     websocket.onopen = (e)=>{
        console.log('로그인 서버 연결');
    }
    websocket.onclose = (e)=>{
        console.log('서버 탈출! ');
    }
    websocket.onmessage = (e)=>{
        console.log(e.data);
    }
    websocket.onerror = (e)=>{
        console.log(e);
    }
    return websocket;
}
*/
export default function Login(props){
    // 탭 전환
    const [value, setValue] = React.useState('1');
    const handleChange = (event, newValue) => {
        setValue(newValue);
    }

    // 일반로그인
    let inputMemail = useRef(null);
    let inputMphone = useRef(null);
    let websocket = null;

    const mlogin = () => {
        let loginForm = document.querySelectorAll(".user")[0];
        let loginFormData = new FormData(loginForm);
        axios.post("/member/login", loginFormData ).then( r=>{
            if(r.data != false){
                alert('로그인 성공');
                window.location.href="/";
            }else{
                alert('동일한 회원정보가 없습니다.');
            }
        })
    }
    function sendWebSocketMessage(message) {
      if (websocket.readyState === WebSocket.OPEN) {
        console.log('Sending message:', message);
        websocket.send(JSON.stringify("qweqweqwe"));
      } else {
        console.log('WebSocket connection is still connecting...');
        setTimeout(() => {
          sendWebSocketMessage(message);
        }, 1000); // Retry after 1 second
      }
    }
    // 의사 로그인
    let inputHmemail = useRef(null);
    let inputHpassword = useRef(null);

    const hlogin = () => {
        let loginForm = document.querySelectorAll(".doctor")[0];
        let loginFormData = new FormData(loginForm);

        axios.post("/member/login", loginFormData ).then( r=>{
            console.log(r.data);
            if(r.data != false){
                alert('로그인 성공');
                window.location.href="/";
            }else{
                alert('로그인 실패');
            }
        })
    }
    // 관리자 로그인
        let inputid = useRef(null);
        let inputpassword = useRef(null);

    const alogin = () => {
        let id = inputid.current.value;       console.log(id);
        let pw = inputpassword.current.value; console.log(pw);
        if( id === "admin" && pw === "admin" ){
            sessionStorage.setItem('email', "admin");
            sessionStorage.setItem('loginType', "admin");
            window.location.href="/";
        }else{
            alert('로그인 실패');
        }
    }

    return(<Container>
            <Box sx={{ width: '100%', typography: 'body1' }}>
            <TabContext value={value}>
                <Box sx={{ borderBottom: 1, borderColor: 'divider' }}>
                <TabList onChange={handleChange} aria-label="lab API tabs example">
                    <Tab label="MAIN"      value="1" />
                    <Tab label="HOSPITAL"  value="2" />
                    <Tab label="ADMIN"     value="3" />
                </TabList>
                </Box>
                <TabPanel value="1">
                    <form className="user">
                        <div style={{display:'flex'}}>
                            <div>
                                <TextField name="email"      label="이메일"   variant="outlined"  inputRef={inputMemail} margin="normal" size="small"/> <br/>
                                <TextField name="password"      label="핸드폰"   variant="outlined"  inputRef={inputMphone} margin="normal" size="small"/>
                            </div>
                            <div style={{marginTop:'20px'}}>
                                <Button variant="contained" onClick={mlogin}
                                    style={{height:'100px', marginLeft:'10px', backgroundColor: '#DCBE70'}}> LOGIN </Button>
                            </div>
                        </div>
                        <Stack sx={{ mt: 2 }} direction="row" spacing={2}>
                            <Button variant="contained" style={{ width: '100%' , backgroundColor: '#F7E111'}} href="/oauth2/authorization/kakao"> KAKAO </Button>
                            <Button variant="contained" style={{ width: '100%' , backgroundColor: '#03C75A'}} href="/oauth2/authorization/naver"> NAVER </Button>
                        </Stack>
                    </form>
                </TabPanel>
                <TabPanel value="2">
                    <form style={{display:'flex'}} className="doctor">
                        <div>
                            <TextField name="email"     label="이메일"   variant="outlined"  inputRef={inputHmemail} margin="normal"  size="small"  /> <br/>
                            <TextField name="password"   label="비밀번호"  variant="outlined"  inputRef={inputHpassword} margin="normal" size="small" />
                        </div>
                        <div style={{marginTop:'20px'}}>
                            <Button variant="contained" onClick={hlogin}
                                style={{height:'100px', marginLeft:'10px', backgroundColor: '#DCBE70'}}> LOGIN </Button>
                        </div>
                    </form>
                </TabPanel>
                <TabPanel value="3">
                    <form style={{display:'flex'}} className="admin">
                        <div>
                            <TextField name="id"          label="아이디"   variant="outlined"  inputRef={inputid}      margin="normal" size="small" /> <br/>
                            <TextField name="password"    label="비밀번호"  variant="outlined" inputRef={inputpassword} margin="normal" size="small" />
                        </div>
                        <div style={{marginTop:'20px'}}>
                            <Button variant="contained" onClick={alogin}
                                style={{height:'100px', marginLeft:'10px', backgroundColor: '#DCBE70'}}> LOGIN </Button>
                        </div>
                    </form>
                </TabPanel>
              </TabContext>
            </Box>
    </Container>)
}