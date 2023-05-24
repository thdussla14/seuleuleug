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

    let hmname = useRef(null);
    let hmphone = useRef(null);
    let hmemail = useRef(null);
    let hmphone2 = useRef(null);

    const findId = ()=>{
        let info = {
            hmname : hmname.current.value,
            hmphone : hmphone.current.value
        }
        axios.post("/hmember/findid", info ).then( r=>{
            console.log(r.data);
            if(r.data != ""){
                alert('회원님의 아이디는 '+r.data+'입니다.');
                window.location.href="/member/login";
            }else{
                alert('해당 정보로 가입된 회원이 없습니다.');
            }
        })
    }
    const findPwd = ()=>{
        let info = {
            hmemail : hmemail.current.value,
            hmphone : hmphone2.current.value
        }
        axios.post("/hmember/findpwd", info ).then( r=>{
            console.log(r.data);
            if(r.data != ""){
                alert('회원님의 임시 비밀번호는 '+r.data+'입니다.');
                window.location.href="/member/login";
            }else{
                alert('해당 정보로 가입된 회원이 없습니다.');
            }
        })
    }

    return(
        <Container>
            <Box sx={{ width: '100%', typography: 'body1' }}>
                <TabContext value={value}>
                    <Box sx={{ borderBottom: 1, borderColor: 'divider' }}>
                        <TabList onChange={handleChange} aria-label="lab API tabs example">
                            <Tab label="아이디 찾기"      value="1" />
                            <Tab label="비밀번호 찾기"  value="2" />
                        </TabList>
                    </Box>
                    <TabPanel value="1">
                        <div style={{display:'flex'}} >
                            <div>
                                <TextField id="hmname"      label="이름"   variant="outlined"  inputRef={hmname} margin="normal" size="small" />
                                <TextField id="hmphone"      label="전화번호"   variant="outlined"  inputRef={hmphone} margin="normal" size="small" />
                            </div>
                            <div style={{marginTop:'20px'}}>
                                <Button variant="contained" onClick={findId}
                                style={{height:'100px', backgroundColor: '#DCBE70'}}> ID 찾기 </Button>
                            </div>
                        </div>
                    </TabPanel>
                    <TabPanel value="2">
                        <div style={{display:'flex'}} >
                            <div>
                                <TextField id="hmemail"      label="이메일"   variant="outlined"  inputRef={hmemail} margin="normal" size="small" />
                                <TextField id="hmphone2"      label="전화번호"   variant="outlined"  inputRef={hmphone2} margin="normal" size="small" />
                            </div>
                            <div style={{marginTop:'20px'}}>
                                <Button variant="contained" onClick={findPwd}
                                style={{height:'100px', backgroundColor: '#DCBE70'}}> PW 찾기 </Button>
                            </div>
                        </div>
                    </TabPanel>
                </TabContext>
            </Box>
        </Container>
    )
}