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
import Hboard from './board/Hboard';
import Comwrite from './board/Comwrite';
import SignUp from './chatting/SignUp';
import HsignUp from './chatting/HsignUp';
import HospitalList from './hospital/HospitalList';


export default function Index(props) {
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

                <Route path="/hospital/hospitallist" element={<HospitalList />} />
            </Routes>
        </BrowserRouter>
    </>)
}