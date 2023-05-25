import React from 'react';
import {useEffect, useState, useRef, useContext} from 'react'
import Container from '@mui/material/Container';
import axios from 'axios';
import TextField from '@mui/material/TextField';
import Button from '@mui/material/Button';
import ButtonGroup from '@mui/material/ButtonGroup';
import Box from '@mui/material/Box';
import Tab from '@mui/material/Tab';
import TabContext from '@mui/lab/TabContext';
import TabList from '@mui/lab/TabList';
import TabPanel from '@mui/lab/TabPanel';
import Stack from '@mui/material/Stack';
//import { LoginContext,LoginListProvider } from './LoginListProvider';

export default function Login(props){

    // 일반로그인
    let inputMemail = useRef(null);
    let inputMphone = useRef(null);
    let websocket = null;

    const mlogin = () => {
        if(inputMemail.current.value == 'admin' && inputMphone.current.value === "admin" ){
            sessionStorage.setItem('email', "admin");
            sessionStorage.setItem('loginType', "admin");
            window.location.href = '/';
        }else{
            let loginForm = document.querySelectorAll(".user")[0];
            let loginFormData = new FormData(loginForm);
            axios.post("/member/login", loginFormData ).then( r=>{
                if(r.data !== false){
                    alert('로그인 성공');
                    axios.get("/member/info").then( r => {console.log(r);
                        if( r.data !== ''){ // 로그인되어 있으면 // 서비스에서 null 이면 js에서 ''이다.
                            // js 로컬 스토리지에 저장
                            if(r.data.split(' ')[0] === 'DOCTOR'){
                                sessionStorage.setItem("email" , r.data.split(' ')[1] );
                                sessionStorage.setItem('loginType', "doctor");
                            }else{
                                sessionStorage.setItem("email" , r.data );
                                sessionStorage.setItem('loginType', "normal");
                            }
                            window.location.href = '/';
                        }

                    })

                }else{
                    alert('동일한 회원정보가 없습니다.');
                }
            })
        }
    }

    return(<Container>
            <form className="user" style={{display: 'flex', flexDirection: 'column', alignItems: 'center'}}>
                <div style={{display:'flex', marginTop:'20px'}}>
                    <div>
                        <TextField name="email"      label="이메일"   variant="outlined"  inputRef={inputMemail} margin="none" size="small"/> <br/>
                        <TextField name="password"   label="비밀번호" variant="outlined"  inputRef={inputMphone}
                            type="password" margin="normal" size="small"/>
                    </div>
                    <div >
                        <Button variant="contained" onClick={mlogin}
                            style={{height:'95px', marginLeft:'10px', backgroundColor: '#DCBE70'}}> LOGIN </Button>
                    </div>
                </div>
                <ButtonGroup variant="text" aria-label="text button group" sx={{ width: '90%', margin:'10px'}} >
                  <Button style={{ width: '100%' }} href="/signup">SignUp</Button>
                  <Button style={{ width: '100%' }} href="/member/find">Find  </Button>
                </ButtonGroup>
                  <Button variant="contained" style={{ width: '90%' , backgroundColor: '#F7E111'}} href="/oauth2/authorization/kakao"> KAKAO </Button> <br/>
                  <Button variant="contained" style={{ width: '90%' }} href="/oauth2/authorization/google"> GOOGLE </Button>
            </form>

    </Container>)
}