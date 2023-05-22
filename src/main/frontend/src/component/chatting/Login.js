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
        if(inputMemail.current.value == 'admin' && inputMphone.current.value === "admin" ){
            sessionStorage.setItem('email', "admin");
            sessionStorage.setItem('loginType', "admin");
            window.location.href="/";
        }else{
            let loginForm = document.querySelectorAll(".user")[0];
            let loginFormData = new FormData(loginForm);
            axios.post("/member/login", loginFormData ).then( r=>{
                if(r.data !== false){
                    alert('로그인 성공');
                    window.location.href="/";
                }else{
                    alert('동일한 회원정보가 없습니다.');
                }
            })
        }
    }

    return(<Container>

            <form className="user" style={{display: 'flex', flexDirection: 'column', alignItems: 'center'}}>
                <div style={{display:'flex'}}>
                    <div>
                        <TextField name="email"      label="이메일"   variant="outlined"  inputRef={inputMemail} margin="normal" size="small"/> <br/>
                        <TextField name="password"   label="핸드폰"   variant="outlined"  inputRef={inputMphone} margin="normal" size="small"/>
                    </div>
                    <div style={{marginTop:'20px'}}>
                        <Button variant="contained" onClick={mlogin}
                            style={{height:'100px', marginLeft:'10px', backgroundColor: '#DCBE70'}}> LOGIN </Button>
                    </div>
                </div>
                <Stack sx={{ mt: 2 }} direction="row" spacing={2}>
                    <Button variant="contained" style={{ width: '100%' , backgroundColor: '#F7E111'}} href="/oauth2/authorization/kakao"> KAKAO </Button>
                    <Button variant="contained" style={{ width: '100%' }} href="/oauth2/authorization/google"> GOOGLE </Button>
                </Stack>
            </form>

    </Container>)
}