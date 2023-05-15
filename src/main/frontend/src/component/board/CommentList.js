import React,{useState,useEffect } from 'react';
import Comment from './Comment'; // 컴포넌트 호출
import axios from 'axios'

export default function CommentList(props){

   const [ item , setItem ] = useState([]);

   // 해당 게시물에 연결된 답변 목록 가져오기
   useEffect(()=>{
        axios.get('/board/getcomment', { params : { bno : props.bno}})
            .then(  r => { console.log(r);
                    if( r == null ){ setItem('null');}
                    else{setItem(r.data) ;}}
            )
            .catch( e => { console.log(e);})
    }, [])

    if( item.length === 0){
        // 답변이 없는 경우
        return(<div> 등록된 답변이 없습니다. </div>)
    }else{
        // 답변 하나씩 Comment 컴포넌트로 보내서 내용물 출력
        return (<div>{item.map((c)=>{return( <Comment comment={c} />)})}</div>)
    }
}

