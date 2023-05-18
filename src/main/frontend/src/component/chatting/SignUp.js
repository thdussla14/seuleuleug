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
import JoinHospitalList from './JoinHospitalList';

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
                alert('회원가입 성공'); window.location.href="/";
            }else{
                alert('회원가입 실패');
            }
        })
    }
    // 의사 회원가입
    const inputform = useRef(null);

    // 카테고리 변경
    let [ hno, setHno ] = useState(0);
    const hospitalChange = (hno) => {
        console.log(hno)
        setHno(hno);
    }
    const hsignup = ()=>{
        const formData = new FormData(inputform.current);
        console.log(formData)
        formData.set("hno", hno);
        axios.post('/hmember/hsignup',formData)
            .then(r=>{
            if( r.data == true){alert('회원 가입 완료');window.location.href="/";}
            else {alert('회원 가입 실패')}})
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
                <div style={{display:'flex'}} >
                    <div>
                        <TextField id="memail"      label="이메일"   variant="outlined"  inputRef={memail} margin="normal" size="small" />
                        <TextField id="mphone"      label="핸드폰"   variant="outlined"  inputRef={mphone} margin="normal" size="small" />
                    </div>
                    <div style={{marginTop:'20px'}}>
                        <Button variant="contained" onClick={signup}
                            style={{height:'56px', margin:'10px', backgroundColor: '#DCBE70'}}> SIGNUP </Button>
                    </div>
                </div>
                </TabPanel>
                <TabPanel value="2">
                    <JoinHospitalList hospitalChange= {hospitalChange} />
                    <form ref={inputform}>
                        <TextField name="hmemail"     label="이메일"    variant="outlined"  margin="normal" size="small" />
                        <TextField name="hpassword"   label="비밀번호"  variant="outlined"  margin="normal" size="small" />
                        <TextField name="hmname"      label="이름"     variant="outlined"  margin="normal" size="small" />
                        <TextField name="hmphone"     label="전화번호"  variant="outlined"  margin="normal" size="small" />
                        <TextField name="doctorpimg"  label="프로필사진"  InputLabelProps={{ shrink: true}} variant="outlined" margin="normal" size="small"
                         accept="image/png, image/jpeg, image/gif"   type="file" />
                        <TextField name="doctorcertification" label="의사증명서"  InputLabelProps={{ shrink: true}} variant="outlined" margin="normal" size="small"
                         accept="image/png, image/jpeg, image/gif"   type="file" />
                        <Button variant="contained" onClick={hsignup}
                            style={{height:'56px', margin:'10px', backgroundColor: '#DCBE70'}}> SIGNUP </Button>
                    </form>
                </TabPanel>
              </TabContext>
            </Box>
    </Container>)
}
