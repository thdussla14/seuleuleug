import React from 'react';
import Avatar from '@mui/material/Avatar';
import Stack from '@mui/material/Stack';
import styles from '../../css/Comment.css';

export default function Comment(props){

    // 답변 레코드 하나씩 전달받기
    const comment = props.comment

    return (
        <div className="wrapper">
           <div>
               <Avatar alt={comment.hmname}  src={'http://localhost:8080/static/media/'+comment.hmpimg} />
           </div>
           <div className="contentContainer">
                <div className="nameText" style={{fontSize:'12px'}}>      {comment.hmname} 의사
                    <span style={{fontSize:'10px', marginLeft:'10px'}}>   {comment.hname}  소속    </span>
                </div>
                <div className="commentText">   {comment.rcontent} </div>
           </div>
        </div>
    )
}
