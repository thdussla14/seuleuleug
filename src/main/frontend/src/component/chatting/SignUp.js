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

    // 의사 회원가입
    const inputform = useRef(null);

    // 카테고리 변경
    let [ hno, setHno ] = useState(0);
    const hospitalChange = (hno) => {
        console.log(hno)
        setHno(hno);
    }

    let hmemail = useRef(null);
    const hsignup = ()=>{
        let memailj = /^[a-zA-Z0-9_-]+@[a-zA-Z0-9-]+\.[a-zA-Z0-9-]+$/
        if( memailj.test(hmemail.current.value) ){
            const formData = new FormData(inputform.current);
            console.log(formData)
            formData.set("hno", hno);
            axios.post('/hmember/hsignup',formData)
                .then(r=>{
                    if( r.data == true){
                        alert('회원 가입 완료');
                        window.location.href="/";
                    }
                    else {
                        alert('이미 사용중인 아이디[이메일] 입니다.');
                    }
            })
        }else{
            alert('이메일 형식에 맞게 입력하여 주십시오.')
        }
    }

    return(
        <Container>
            <JoinHospitalList hospitalChange= {hospitalChange} />
            <form ref={inputform}>
                <TextField name="hmemail"     label="이메일"    variant="outlined"  margin="normal" size="small" inputRef={hmemail} />
                <TextField name="hpassword"   label="비밀번호"  variant="outlined"  margin="normal" size="small" />
                <TextField name="hmname"      label="이름"     variant="outlined"  margin="normal" size="small" />
                <TextField name="hmphone"     label="전화번호"  variant="outlined"  margin="normal" size="small" />
                <TextField name="doctorpimg"  label="프로필사진"  InputLabelProps={{ shrink: true}}  variant="outlined" margin="normal" size="small"
                    accept="image/png, image/jpeg, image/gif"   type="file" />
                <TextField name="doctorcertification" label="의사증명서" InputLabelProps={{ shrink: true}}  variant="outlined" margin="normal" size="small"
                    accept="image/png, image/jpeg, image/gif"   type="file" />
                <Button variant="contained" onClick={hsignup}
                    style={{height:'56px', margin:'10px', backgroundColor: '#DCBE70'}}> SIGNUP </Button>
            </form>
        </Container>
    )
}
