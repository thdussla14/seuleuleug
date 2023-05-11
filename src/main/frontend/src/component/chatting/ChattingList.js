import React from 'react';
import {useEffect, useState, useRef} from 'react'
import Container from '@mui/material/Container';
import Chat from './Chat';

export default function ChattingList(props){

    //let [onList, setOnList]] = useState([]);

    return (<>
        <div>
           <a href={"/chatting/1"}>1번방</a>
           <a href={"/chatting/2"}>2번방</a>
           <a href={"/chatting/3"}>3번방</a>
           <a href={"/chatting/4"}>4번방</a>
        </div>
    </>)
}