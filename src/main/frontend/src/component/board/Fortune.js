import React, { useState } from 'react';
import axios from 'axios';
import styles from '../../css/main.css';

export default function Fortune(props) {

  const [message, setMessage] = useState('Click me!');
  const [clicked, setClicked] = useState(false);

  const openCookie = () => {
    axios.get("/word").then( r => { console.log(r); setMessage(r.data.wcontent) ; setClicked(true); })
  };

  return (
    <div className={`cookie ${clicked ? 'clicked' : ''}`} onClick={openCookie}>
       <div className="cookie-message">{message}</div>
    </div>
  );
}
