import React from 'react';
import {useEffect, useState, useRef} from 'react'
import Container from '@mui/material/Container';
import axios from 'axios';

export default function HsignUp(props){

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

    return(<>
        <Container>
            <h5> 회원가입 페이지 </h5>
                <input ref={hmemail} className="hmemail" type="text" placeHolder="이메일" />
                <input ref={hpassword} className="hpassword" type="text" placeHolder="비밀번호" />
                <input ref={hmname} className="hmname" type="text" placeHolder="이름" />
                <input ref={hmphone} className="hmphone" type="text" placeHolder="전화번호" />
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
                <button onClick={hsignup} type="button">회원가입</button>
        </Container>
    </>)
}