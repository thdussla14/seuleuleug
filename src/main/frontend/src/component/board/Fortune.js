import React, { useEffect } from 'react';
import axios from 'axios';
import styles from '../../css/main.css';
import fortunecookie from '../../fortunecookie.png';  // img 호출

export default function Fortune(props) {


  useEffect(()=>{
       axios.get("/word")
            .then( r => { console.log(r); document.querySelector('.cookie-message').innerHTML = r.data.wcontent  ;})
  }, [])

  return (
    <div>
       <div>
            <img style={{width:'300px'}} src={fortunecookie}/>
       </div>
       <div className="cookie-message"></div>
    </div>
  );
}
