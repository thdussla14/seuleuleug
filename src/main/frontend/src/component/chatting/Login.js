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

export default function Login(props){

    // 탭 전환
    const [value, setValue] = React.useState('1');
    const handleChange = (event, newValue) => {
        setValue(newValue);
    }

    // 일반로그인
    let inputMemail = useRef(null);
    let inputMphone = useRef(null);

    const mlogin = () => {

       let memail = inputMemail.current.value;
       let mphone = inputMphone.current.value;

        axios.get("/member/login", {params : { "memail" : memail , "mphone" : mphone }} ).then( r=>{
            console.log(r.data);
            if(r.data!==null){
                alert('로그인 성공');
                sessionStorage.setItem('email', memail);
                sessionStorage.setItem('loginType', "normal");
                window.location.href="/";
            }else{
                alert('로그인 실패');
            }
        })
    }
    // 의사 로그인
    let inputHmemail = useRef(null);
    let inputHpassword = useRef(null);

    const hlogin = () => {

       let hmemail = inputHmemail.current.value;
       let hpassword = inputHpassword.current.value;

        axios.get("/hmember/hlogin", {params : { "hmemail" : hmemail , "hpassword" : hpassword }} ).then( r=>{
            console.log(r.data);
            if(r.data!==null){
                alert('로그인 성공');
                sessionStorage.setItem('email', hmemail);
                sessionStorage.setItem('loginType', "doctor");
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
                    <div style={{display:'flex'}}>
                        <div>
                            <TextField id="memail"      label="이메일"   variant="outlined"  inputRef={inputMemail} margin="normal" size="small"/> <br/>
                            <TextField id="mphone"      label="핸드폰"   variant="outlined"  inputRef={inputMphone} margin="normal" size="small"/>
                        </div>
                        <div style={{marginTop:'20px'}}>
                            <Button variant="contained" onClick={mlogin}
                                style={{height:'100px', marginLeft:'10px', backgroundColor: '#DCBE70'}}> LOGIN </Button>
                        </div>
                    </div>
                </TabPanel>
                <TabPanel value="2">
                    <div style={{display:'flex'}}>
                        <div>
                             <TextField id="hmemail"     label="이메일"   variant="outlined"  inputRef={inputHmemail} margin="normal"  size="small"  /> <br/>
                             <TextField id="hpassword"   label="비밀번호"  variant="outlined"  inputRef={inputHpassword} margin="normal" size="small" />
                        </div>
                        <div style={{marginTop:'20px'}}>
                            <Button variant="contained" onClick={hlogin}
                                style={{height:'100px', marginLeft:'10px', backgroundColor: '#DCBE70'}}> LOGIN </Button>
                        </div>
                    </div>
                </TabPanel>
                <TabPanel value="3">
                    <div style={{display:'flex'}}>
                        <div>
                            <TextField id="id"          label="아이디"   variant="outlined"  inputRef={inputid}      margin="normal" size="small" /> <br/>
                            <TextField id="password"    label="비밀번호"  variant="outlined" inputRef={inputpassword} margin="normal" size="small" />
                        </div>
                        <div style={{marginTop:'20px'}}>
                            <Button variant="contained" onClick={alogin}
                                style={{height:'100px', marginLeft:'10px', backgroundColor: '#DCBE70'}}> LOGIN </Button>
                        </div>
                    </div>
                </TabPanel>
              </TabContext>
            </Box>
    </Container>)
}
