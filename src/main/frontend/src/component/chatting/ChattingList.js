import React from 'react';
import axios from 'axios';
import {useEffect, useState, useRef} from 'react'
import Container from '@mui/material/Container';
import Chat from './Chat';
import ChatRoom from './ChatRoom';

export default function ChattingList(props){

    const [chattingRoomList , setChattingRoomList ] = useState([]);
    useEffect(()=>{
        axios.get("/chatting/chatlist").then( r=>{
                console.log(r.data);
                setChattingRoomList(r.data)
            })
    },[]);

    console.log(chattingRoomList);

    /*const printList = () =>{
        console.log(chattingRoomList)
         let chattinglist = chattingRoomList.map( (o)=>{
           return (<div> qwe : {o.chatRoomId} </div>)
        })
        console.log(chattinglist)
        return chattinglist;
    }*/

    //let [onList, setOnList]] = useState([]);

    return (<>
        <ChatRoom chattingRoomList={chattingRoomList}/>
    </>)
}