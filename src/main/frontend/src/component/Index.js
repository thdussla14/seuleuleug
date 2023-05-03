import React from 'react';
import {BrowserRouter , Routes , Route } from 'react-router-dom';
import Header from './Header';
import Main from './Main';
import SignUp from './chatting/SignUp';


export default function Index(props) {
    return (<>
        <BrowserRouter>
            <Header />
            <Routes >
                <Route path="/" element={<Main />} />
                <Route path="/signup" element={<SignUp />} />
            </Routes>
        </BrowserRouter>
    </>)
}