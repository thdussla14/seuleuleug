import React from 'react';
import {BrowserRouter , Routes , Route } from 'react-router-dom';
import Header from './Header';
import Main from './Main';
import DashBoard from './admin/DashBoard';
import Write from './board/Write';
import CheckEmail from './board/CheckEmail';
import MyBoardList from './board/MyBoardList';
import MyBoard from './board/MyBoard';
import BoardList from './board/BoardList';
import HBoard from './board/HBoard';
import Comwrite from './board/Comwrite';
import SignUp from './chatting/SignUp';
import Login from './chatting/Login';
import ChattingList from './chatting/ChattingList';
import Chat from './chatting/Chat';
import HospitalList from './hospital/HospitalList';
import GovernmentInfo from './info/GovernmentInfo';
import ChallengeWrite from './challenge/ChallengeWrite';
import Challenge from './challenge/Challenge';
import SimriTest from './info/SimriTest';
import ChallengeDetail from './challenge/ChallengeDetail.js'
import ChallengeResultList from'./challenge/ChallengeResultList.js'
//import { LoginContext, LoginListProvider} from './chatting/LoginListProvider';
import Find from './chatting/Find';

export default function Index(props) {
    return (<>
    {/*<LoginListProvider>*/}
        <BrowserRouter>
            <Header />
            <Routes >
                <Route path="/"                         element={<Main />} />
                <Route path="/admin/dashboard"          element={<DashBoard />} />
                <Route path="/board/write"              element={<Write />} />
                <Route path="/board/checkemail"         element={<CheckEmail />} />
                <Route path="/board/myboardlist"        element={<MyBoardList />} />
                <Route path="/board/myboard"            element={<MyBoard />} />
                <Route path="/board/doctor/boardlist"   element={<BoardList />} />
                <Route path="/board/doctor/hboard"      element={<HBoard />} />
                <Route path="/board/doctor/comwrite"    element={<Comwrite />} />

                <Route path="/signup"                   element={<SignUp />} />
                <Route path="/member/login"             element={<Login />} />
                <Route path="/member/find"              element={<Find />} />

                <Route path="/chattinglist"             element={<ChattingList />} />
                <Route path="/chatting/:chatRoomId"     element={<Chat />} />

                <Route path="/hospital/hospitallist"    element={<HospitalList />} />
                <Route path="/simritest/info"           element={<SimriTest />} />

                <Route path="/government/info"          element={<GovernmentInfo />} />
                <Route path="/challenge/challengewrite" element={<ChallengeWrite />} />
                <Route path="/challenge/challenge"      element={<Challenge />} />
                <Route path="/challenge/challengedetail" element={<ChallengeDetail />} />
                <Route path="/challenge/challengeResultList" element={<ChallengeResultList />} />
            </Routes>
        </BrowserRouter>
    {/*</LoginListProvider>*/}

    </>)
}