import React, {useState, useEffect} from 'react';
import axios from 'axios';
import Container from '@mui/material/Container';
export default function Header(props) {

    return (
    <Container>
        <div>
            <a href="/" > Home </a>
            <a href="/hospital/hospitallist" > Hospital </a>
        </div>
    </Container>
    )
}