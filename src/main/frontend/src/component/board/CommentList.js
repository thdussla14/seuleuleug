import React,{useState,useEffect } from 'react';
import Comment from './Comment'; // 컴포넌트 호출
import axios from 'axios'

export default function CommentList(props){

    const [ item , setItem ] = useState([]);

/*    let r =[
        { name:"유재석" ,comment:"봄"   },
        { name:"민경훈" ,comment:"사랑" },
        { name:"서장훈" ,comment:"벚꽃" },
        { name:"강호동" ,comment:"말고 ^▼^ " },
    ]*/

   useEffect(()=>{
        axios.get('/board/getcomment', { params : { bno : props.bno}})
            .then(  r => { console.log(r); setItem(r.data) ;})
            .catch( e => { console.log(e);})
    }, [])

    return (
        <div>
            {
                item.map((c)=>{
                    return( <Comment comment={c} />)
                })
            }
        </div>
    )
}