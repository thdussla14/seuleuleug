import React from 'react';
import { Link } from 'react-router-dom';
import axios from 'axios';

export default function ChatRoom(props) {
    let chattingList = props.chattingRoomList.map((o) => {

        console.log(o)
        console.log(o.chatRoomId)
        let hmname = null;
        axios.get("/hmember/hcomment",{params : { hmemail : o.chatRoomId}}).then( r => {
            console.log(r.data);
            hmname = r.data.hmname;
        })

        return (
            <div className={o.chatRoomId} >
                <Link to={`/chatting/${o.chatRoomId}`} style={{color : 'black'}} >
                {hmname} 의사 선생님
                </Link>
            </div>
        );
    });

    return (<>{chattingList}</>);
}