import React, { useEffect } from 'react';
import axios from 'axios';
import fortunecookie from '../../fortunecookie.png';  // img 호출

export default function Fortune(props) {

 // 응원문구 랜덤 출력
  useEffect(()=>{
       axios.get("/word")
            .then( r => {console.log(r);
                document.querySelector('.cookie-message').innerHTML = r.data.wcontent;
                //dangerouslySetInnerHTML={ __html : r.data.wcontent }
            })
  }, [])

  return (
    <div style={{display:'flex', padding: '20px'}}>
       <img style={{ width:'30%'}} alt="fortune" src={fortunecookie}/>
       <div className="cookie-message">  </div>
    </div>
  );
}
