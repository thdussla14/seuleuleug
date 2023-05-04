import React from 'react';
import {BrowserRouter , Routes , Route } from 'react-router-dom';
import Header from './Header';
import Main from './Main';
import DashBoard from './admin/DashBoard';
import Write from './board/Write';
import SignUp from './chatting/SignUp';
import HsignUp from './chatting/HsignUp';


export default function Index(props) {
    return (<>
        <BrowserRouter>
            <Header />
            <Routes >
                <Route path="/"                 element={<Main />} />
                <Route path="/admin/dashboard"  element={<DashBoard />} />
                <Route path="/board/write"      element={<Write />} />
                <Route path="/" element={<Main />} />
                <Route path="/signup" element={<SignUp />} />
                <Route path="/hsignup" element={<HsignUp />} />
            </Routes>
        </BrowserRouter>
    </>)
}