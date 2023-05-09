import React from 'react';
import {useEffect, useState, useRef} from 'react'
import Container from '@mui/material/Container';
import Chat from './Chat';

export default function ChattingList(props){

    //let [onList, setOnList]] = useState([]);

    return (<>
        <div>
           <a href={"/chat/1"}>1번방</a>
           <a href={"/chat/2"}>2번방</a>
           <a href={"/chat/3"}>3번방</a>
           <a href={"/chat/4"}>4번방</a>
        </div>
    </>)
}