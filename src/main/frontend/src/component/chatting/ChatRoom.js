import React, { useEffect, useState, useContext  } from 'react';
import axios from 'axios';
import { Link } from 'react-router-dom';
import doctor from '../../doctor.png';
import Avatar from "@mui/material/Avatar";
import { WebSocketContext } from './WebSocketContext';

export default function ChatRoom(props) {
    const websocket = useContext(WebSocketContext);
    console.log(websocket)
    const [chattingList, setChattingList] = useState([]);
    console.log(props.chattingRoomList);
      // 의사에게 상담요청 보내는 함수
    const counsel = (email)=>{
        //const websocket = JSON.parse(sessionStorage.getItem('websocket'))
        if(sessionStorage.getItem("email")==="null"||websocket==="null"){
            alert('로그인 후 사용 가능한 기능입니다.')
            window.location.href="/member/login";
        }
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
              console.log(o.chatting)
              let hmname = null;
              const response = await axios.get("/hmember/hcomment", { params: { hmemail: o.userEmail } });
              console.log(response.data);
              return (
                <div key={o.chatRoomId} className={o.chatRoomId}>
                    <div style={{display:'flex'}}>
                      <Avatar alt="Remy Sharp"      src={response.data.hmpimg} />
                        <div>
                            <div> {response.data.hmname} 의사 선생님</div>
                            <div>소속병원 : {response.data.hname}</div>
                       </div>
                      {
                            (o.chatting===false||o.chatting==="false")?
                            <button onClick={() => counsel(o.userEmail)} type="button"> 상담신청 </button>
                            : <button disabled="disabled" type="button"> 상담중 </button>
                      }
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