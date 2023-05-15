import React, { useEffect } from 'react';
import axios from 'axios';
import fortunecookie from '../../fortunecookie.png';  // img 호출

export default function Fortune(props) {

  // 응원문구 랜덤 출력
  useEffect(()=>{
       axios.get("/word")
            .then( r => { console.log(r); document.querySelector('.cookie-message').innerHTML = r.data.wcontent  ;})
  }, [])

  return (
    <div>
       <div>
            <img style={{ width:'100%', marginTop:'30px' }} src={fortunecookie}/>
       </div>
       <div className="cookie-message"></div>
    </div>
  );
}
