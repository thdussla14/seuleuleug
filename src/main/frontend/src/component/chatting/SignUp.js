import React from 'react';
import {useEffect, useState, useRef} from 'react'
import Container from '@mui/material/Container';
import axios from 'axios';
import TextField from '@mui/material/TextField';
import Button from '@mui/material/Button';
import Box from '@mui/material/Box';
import Tab from '@mui/material/Tab';
import TabContext from '@mui/lab/TabContext';
import TabList from '@mui/lab/TabList';
import TabPanel from '@mui/lab/TabPanel';

export default function SingUp(props){

    // 탭 전환
    const [value, setValue] = React.useState('1');
    const handleChange = (event, newValue) => {
        setValue(newValue);
    }

    // 일반 회원가입
    let memail = useRef(null);
    let mphone = useRef(null);

    const signup = ()=>{

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
    // 의사 회원가입
    let hmemail = useRef(null);
    let hpassword = useRef(null);
    let hmname = useRef(null);
    let hmphone = useRef(null);
    let hmcertification = useRef(null);
    let inputform = useRef(null);

    const hsignup = ()=>{

        console.log(hmcertification.current.value);
        console.log(hmcertification.current.name);
        console.log(hmcertification.current.files[0]);
        let file = hmcertification.current.files[0];

        let info = {
            hmemail : hmemail.current.value,
            hpassword : hpassword.current.value,
            hmname : hmname.current.value,
            hmphone : hmphone.current.value,
            hmcertification : file.name
        }

        axios.post("/hmember/hsignup", info ).then( r=>{
            console.log(r.data);
            if(r.data==true){
                alert('회원가입 성공');
                {/*window.location.href="/";*/}
            }else{
                alert('회원가입 실패');
            }
        })
        let formData = new FormData(inputform.current);
        axios.post("/hmember/files", formData).then( r =>{
            console.log(r.data);
        })
    }


    return(<Container>
            <Box sx={{ width: '100%', typography: 'body1' }}>
              <TabContext value={value}>
                <Box sx={{ borderBottom: 1, borderColor: 'divider' }}>
                  <TabList onChange={handleChange} aria-label="lab API tabs example">
                    <Tab label="MAIN"      value="1" />
                    <Tab label="HOSPITAL"  value="2" />
                  </TabList>
                </Box>
                <TabPanel value="1">
                    <TextField id="memail"      label="이메일"   variant="outlined"  inputRef={memail} margin="normal" size="small" />
                    <TextField id="mphone"      label="핸드폰"   variant="outlined"  inputRef={mphone} margin="normal" size="small" /> <br/>
                    <Button variant="contained" onClick={signup}
                        style={{height:'56px', margin:'10px', backgroundColor: '#DCBE70'}}> SIGNUP </Button>
                </TabPanel>
                <TabPanel value="2">
                    <TextField id="hmemail"     label="이메일"   variant="outlined"   inputRef={hmemail}   margin="normal" size="small" />
                    <TextField id="hpassword"   label="비밀번호"  variant="outlined"  inputRef={hpassword} margin="normal" size="small" />
                    <TextField id="hmname"      label="이름"     variant="outlined"  inputRef={hmname}    margin="normal" size="small" />
                    <TextField id="hmphone"     label="전화번호"  variant="outlined"  inputRef={hmphone}   margin="normal" size="small" />
                    <div>
                        <h5>의사 자격증 제출란</h5>
                        <form ref={inputform}>
                            <input
                                ref={hmcertification}
                                type="file"
                                name="hmcertification"
                            />
                        </form>
                    </div>
                    <Button variant="contained" onClick={hsignup}
                        style={{height:'56px', margin:'10px', backgroundColor: '#DCBE70'}}> SIGNUP </Button>
                </TabPanel>
              </TabContext>
            </Box>
    </Container>)
}
