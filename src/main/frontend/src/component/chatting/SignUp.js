import React from 'react'
import {useEffect, useState, useRef} from 'react'
import Container from '@mui/material/Container';

export default function SingUp(props){

    const signup = ()=>{
        axios.post("")
    }

    return(<>
        <div>
            <h5> 회원가입 페이지 </h5>
            <input className="memail" type="text" placeHolder="이메일" />
            <input className="mphone" type="text" placeHolder="전화번호" />
            <button onClick={signup} type="button">회원가입</button>
        </div>
    </>)
}