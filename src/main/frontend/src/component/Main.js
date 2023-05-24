import React, { useEffect, useState, useContext  } from 'react';
import axios from 'axios';
import Container from '@mui/material/Container';
import styles from '../css/main.css';
import Fortune from './board/Fortune';
import Avatar from '@mui/material/Avatar';
import Stack from '@mui/material/Stack';
import doctor from '../doctor.png';  // img 호출
import MainChat from './chatting/MainChat';
import Chip from '@mui/material/Chip';
import FaceIcon from '@mui/icons-material/Face';

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
                    <Fortune />
                </div>
                <div>
                    심리상담의 사회적 편견에서 벗어나, <br/>
                    자신의 고민을 말하는데 부담이 없고 <br/>
                    심리상담의 허들을 낮출 수 있는 곳
                </div>
                <Chip icon={<FaceIcon />} label="실시간 상담 가능 의사"
                    style={{position: 'relative', top: '68px', left: '-74px', background: 'lemonchiffon', border:'1px solid black'}} />
                <Stack direction="row" spacing={2}
                    justifyContent="center"
                    alignItems="center"
                    height= "112px"
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