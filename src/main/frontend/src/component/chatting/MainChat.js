import React, { useEffect, useState, useContext  } from 'react';
import axios from 'axios';
import { Link } from 'react-router-dom';
import doctor from '../../doctor.png';
import Avatar from "@mui/material/Avatar";
import { WebSocketContext } from './WebSocketContext';

export default function MainChat(props) {
    const websocket = useContext(WebSocketContext);
    console.log(websocket)
    const [chattingList, setChattingList] = useState([]);
    console.log(props.chattingRoomList);
      // 의사에게 상담요청 보내는 함수
    const counsel = (email)=>{
        //const websocket = JSON.parse(sessionStorage.getItem('websocket'))
        console.log(websocket);
        console.log(email);
        websocket.send(JSON.stringify({ type : "counsel", doctor : email, normal : sessionStorage.getItem("email") }));
    }

    useEffect(() => {
    console.log(props.chattingRoomList);
    async function fetchData() {
      try {
        const promises = props.chattingRoomList.map(async (o) => {
          console.log(o);
          console.log(o.userEmail);
          let hmname = null;
          const response = await axios.get("/hmember/hcomment", { params: { hmemail: o.userEmail } });
          console.log(response.data);
          hmname = response.data.hmname;

          return (
            <div key={o.chatRoomId} className={o.chatRoomId}>
                <div>
                  <Avatar alt="Remy Sharp"      src={'/static/media/'+response.data.hmpimg} />
                </div>
            </div>
          );
        });

        const results = await Promise.all(promises);
        setChattingList(results);
      } catch (error) {
        console.error(error);
      }
    }

    fetchData();
    }, [props.chattingRoomList]);

  return <>{chattingList}</>;
}