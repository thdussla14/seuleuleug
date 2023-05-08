import React, {useState, useEffect, useRef} from 'react';
import axios from 'axios';
import Container from '@mui/material/Container';
import styles from '../css/main.css';


export default function Main(props) {

    useEffect(()=>{
       axios.get("/word").then( r => { console.log(r); })
    },[])

    return (
        <Container>
            <div className="Main">
                <div className="fortune">
                    <h3> 포춘쿠키 </h3>
                    <div> </div>
                </div>
                <div className="btnBox">
                    <div className="bwrite"> <a href="/board/write">  글쓰기 </a> </div>
                    <div className="MY">     <a href="/board/checkemail"> MY </a>  </div>
                </div>
            </div>
        </Container>
    );
}