import React, {useState, useEffect, useRef} from 'react';
import {BrowserRouter , Routes , Route } from 'react-router-dom';
import Header from './Header';
import Main from './Main';
import DashBoard from './admin/DashBoard';
import Write from './board/Write';
import CheckEmail from './board/CheckEmail';
import MyBoardList from './board/MyBoardList';
import MyBoard from './board/MyBoard';
import BoardList from './board/BoardList';
import Hboard from './board/Hboard';
import Comwrite from './board/Comwrite';
import SignUp from './chatting/SignUp';
import HsignUp from './chatting/HsignUp';
import Hlogin from './chatting/Hlogin';
import Login from './chatting/Login';
import ChattingList from './chatting/ChattingList';
import Chat from './chatting/Chat';
import HospitalList from './hospital/HospitalList';
import GovernmentInfo from './info/GovernmentInfo';
import ChallengeWrite from './challenge/ChallengeWrite';
import Challenge from './challenge/Challenge';
import SimriTest from './info/SimriTest';
import ChallengeDetail from './challenge/ChallengeDetail.js'

export default function Index(props) {

    const socketRef = useRef(null);
    useEffect(()=>{
        if (!socketRef.current) {
            socketRef.current = new WebSocket("ws://localhost:8080/intoHomePage");
            socketRef.current.onopen = (e) => {
              console.log('로그인 서버 연결');
            };
            socketRef.current.onerror = (e) => {
              console.log('로그인 서버 에러');
            };
            socketRef.current.onmessage = (e) => {
              console.log('로그인 서버에서 메세지 전달');
              console.log(e.data);
            };
          }
      return () => {
        // Cleanup code when the component is unmounted
        socketRef.current.onclose = null;
      };
    },[])

    return (<>
        <BrowserRouter>
            <Header />
            <Routes >
                <Route path="/"                  element={<Main />} />
                <Route path="/admin/dashboard"   element={<DashBoard />} />
                <Route path="/board/write"       element={<Write />} />
                <Route path="/board/checkemail"  element={<CheckEmail />} />
                <Route path="/board/myboardlist" element={<MyBoardList />} />
                <Route path="/board/myboard"     element={<MyBoard />} />
                <Route path="/board/boardlist"   element={<BoardList />} />
                <Route path="/board/hboard"      element={<Hboard />} />
                <Route path="/board/comwrite"    element={<Comwrite />} />
                <Route path="/signup" element={<SignUp />} />
                <Route path="/hsignup" element={<HsignUp />} />
                <Route path="/hlogin" element={<Hlogin socketRef={socketRef} />} />
                <Route path="/login" element={<Login socketRef={socketRef} />} />
                <Route path="/chattinglist" element={<ChattingList />} />
                <Route path="/chatting/:chatRoomId" element={<Chat />} />
                <Route path="/hospital/hospitallist" element={<HospitalList />} />
                <Route path="/simritest/info" element={<SimriTest />} />

                <Route path="/government/info" element={<GovernmentInfo />} />
                <Route path="/challenge/challengewrite" element={<ChallengeWrite />} />
                <Route path="/challenge/challenge" element={<Challenge />} />
                <Route path="/challenge/challengedetail" element={<ChallengeDetail />} />
            </Routes>
        </BrowserRouter>
    </>)
}