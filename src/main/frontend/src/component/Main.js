import React, { useEffect, useState, useContext  } from 'react';
import axios from 'axios';
import Container from '@mui/material/Container';
import styles from '../css/main.css';
import Fortune from './board/Fortune';
import Avatar from '@mui/material/Avatar';
import Stack from '@mui/material/Stack';
import doctor from '../doctor.png';  // img 호출
import MainChat from './chatting/MainChat';

export default function Main(props) {

    const [chattingRoomList , setChattingRoomList ] = useState([]);
    useEffect(()=>{
        axios.get("/chatting/logindoctor").then( r=>{
                console.log(r.data);
                setChattingRoomList(r.data)
            })
    },[]);

    const logemail = sessionStorage.getItem("email"); console.log(logemail);

    return (
        <Container>
            <div className="Main">
                <div className="Fortune">
                    <h3> Fortune Cookie </h3>
                    <Fortune />
                </div>
                <Stack direction="row" spacing={2}
                    justifyContent="center"
                    alignItems="center"
                    height= "80px"
                    borderRadius="20px"
                    margin="50px 0px"
                    backgroundColor="white"
                >
                <MainChat chattingRoomList={chattingRoomList}/>
                </Stack>
                <div className="btnBox">
                    <div className="bwrite"> <a href={ logemail == "null" ? "/board/write" :"/board/user/userwrite" }>        글쓰기   </a> </div>
                    <div className="MY">     <a href={ logemail == "null" ? "/board/checkemail" : "/board/myboardlist?bemail="+logemail }>    MY     </a> </div>
                </div>
            </div>
        </Container>
    );
}