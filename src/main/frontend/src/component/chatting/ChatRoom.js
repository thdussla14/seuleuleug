import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { Link } from 'react-router-dom';
import doctor from '../../doctor.png';
import Avatar from "@mui/material/Avatar";


export default function ChatRoom(props) {
  const [chattingList, setChattingList] = useState([]);
  // 의사에게 상담요청 보내는 함수
  const counsel = (email)=>{
        const websocket = JSON.parse(sessionStorage.getItem('websocket'))
        console.log(websocket);
        websocket.current.send(JSON.stringify({ type : "counsel", toEmail : email,receiveEmail : sessionStorage.getItem("email") }));
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
              <Link to={`/chatting/${o.chatRoomId}`} style={{ color: 'black' }}>
                <div>
                  <Avatar alt="Remy Sharp"      src={doctor} />
                  {hmname} 의사 선생님
                  <button onClick={counsel(o.hmeamil)} type="button"> 상담신청 </button>
                </div>

              </Link>
            </div>
          );
        });

        const results = await Promise.all(promises);
        setChattingList(results);
      } catch (error) {
        console.error(error);
        // Handle error
      }
    }

    fetchData();
  }, [props.chattingRoomList]);

  return <>{chattingList}</>;
}