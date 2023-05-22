import React from 'react';
import axios from 'axios';
import {useEffect, useState} from 'react'
import ChatRoom from './ChatRoom';

export default function ChattingList(props){
    if(sessionStorage.getItem("email")==null){
        alert("로그인 후 이용 가능합니다.");
        window.location.href='/';
    }

    const [chattingRoomList , setChattingRoomList ] = useState([]);
    useEffect(()=>{
        axios.get("/chatting/logindoctor").then( r=>{
                console.log(r.data);
                setChattingRoomList(r.data)
            })
    },[]);

    console.log(chattingRoomList);

    return (<>
        <ChatRoom chattingRoomList={chattingRoomList}/>
    </>)
}