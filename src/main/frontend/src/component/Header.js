import React, {useState, useEffect} from 'react';
import axios from 'axios';
import Container from '@mui/material/Container';
import styles from '../css/main.css';

export default function Header(props) {

    return (
    <Container>
        <div className="header" >
            <a href="/" > Home </a>
            <a href="/admin/dashboard" > 관리자 </a>
            <a href="/hospital/hospitallist" > Hospital </a>
        </div>
    </Container>
    )
}