import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { Link } from 'react-router-dom';

export default function ChatRoom(props) {
  const [chattingList, setChattingList] = useState([]);

  useEffect(() => {
    async function fetchData() {
      try {
        const promises = props.chattingRoomList.map(async (o) => {
          console.log(o);
          console.log(o.chatRoomId);
          let hmname = null;
          const response = await axios.get("/hmember/hcomment", { params: { hmemail: o.chatRoomId } });
          console.log(response.data);
          hmname = response.data.hmname;

          return (
            <div key={o.chatRoomId} className={o.chatRoomId}>
              <Link to={`/chatting/${o.chatRoomId}`} style={{ color: 'black' }}>
                {hmname} 의사 선생님
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