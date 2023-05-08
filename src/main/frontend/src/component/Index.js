import React from 'react';
import {BrowserRouter , Routes , Route } from 'react-router-dom';
import Header from './Header';
import Main from './Main';
import SignUp from './chatting/SignUp';
import HsignUp from './chatting/HsignUp';
import Hlogin from './chatting/Hlogin';
import Login from './chatting/Login';


export default function Index(props) {
    return (<>
        <BrowserRouter>
            <Header />
            <Routes >
                <Route path="/" element={<Main />} />
                <Route path="/signup" element={<SignUp />} />
                <Route path="/hsignup" element={<HsignUp />} />
                <Route path="/hlogin" element={<Hlogin />} />
                <Route path="/login" element={<Login />} />
            </Routes>
        </BrowserRouter>
    </>)
}