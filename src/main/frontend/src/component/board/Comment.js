import React from 'react';
import styles from '../../css/Comment.css'; // css 호출
import logo from '../../logo.svg';  // img 호출

export default function Comment(props){

    // 답변 레코드 하나씩 전달받기
    const comment = props.comment

    return (
        <div className="wrapper">
           <div>
                <img src={logo} className="logoimg" />
           </div>
           <div className="contentContainer">
                <div className="nameText">      홍길동 의사   </div>
                <div className="commentText">   {comment.rcontent} </div>
           </div>
        </div>
    )
}
