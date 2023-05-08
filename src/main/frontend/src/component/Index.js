import React from 'react';
import {BrowserRouter , Routes , Route } from 'react-router-dom';
import Header from './Header';
import Main from './Main';
import DashBoard from './admin/DashBoard';
import Write from './board/Write';
import CheckEmail from './board/CheckEmail';
import MyBoardList from './board/MyBoardList';
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


                <Route path="/signup" element={<SignUp />} />
                <Route path="/hsignup" element={<HsignUp />} />

                <Route path="/hospital/hospitallist" element={<HospitalList />} />
            </Routes>
        </BrowserRouter>
    </>)
}