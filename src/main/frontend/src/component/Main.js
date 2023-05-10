import React, {useState, useEffect, useRef} from 'react';
import axios from 'axios';
import Container from '@mui/material/Container';
import styles from '../css/main.css';
import Fortune from './board/Fortune';
import fortunecookie from '../fortunecookie.png';  // img 호출

export default function Main(props) {

    return (
        <Container>
            <div className="Main">
                <div className="Fortune">
                    <img style={{width:'300px'}} src={fortunecookie}/>
                </div>
                <div className="btnBox">
                    <div className="bwrite"> <a href="/board/write">  글쓰기 </a> </div>
                    <div className="MY">     <a href="/board/checkemail"> MY </a>  </div>
                </div>
            </div>
        </Container>
    );
}