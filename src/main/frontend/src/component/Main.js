import React, {useState, useEffect, useRef} from 'react';
import axios from 'axios';
import Container from '@mui/material/Container';
import styles from '../css/main.css';
import Fortune from './board/Fortune';
import Avatar from '@mui/material/Avatar';
import Stack from '@mui/material/Stack';
import monkey from '../monkey.png';  // img 호출

export default function Main(props) {

    return (
        <Container>
            <div className="Main">
                <div className="Fortune">
                    <h3> Fortune Cookie </h3>
                    <Fortune />
                </div>
                <Stack direction="row" spacing={2} justifyContent="center" alignItems="center" >
                  <Avatar alt="Remy Sharp"      src={monkey} />
                  <Avatar alt="Travis Howard"   src={monkey}  />
                  <Avatar alt="Cindy Baker"     src={monkey}  />
                </Stack>
                <div className="btnBox">
                    <div className="bwrite"> <a href="/board/write">        글쓰기   </a> </div>
                    <div className="MY">     <a href="/board/checkemail">    MY     </a> </div>
                </div>
            </div>
        </Container>
    );
}