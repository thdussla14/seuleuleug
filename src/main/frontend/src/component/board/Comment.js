import React from 'react';
import styles from './Comment.css'; // css 호출
import logo from '../../logo.svg';  // img 호출

export default function Comment(props){
    return (
        <div className="wrapper">
           <div>
                <img src={logo} className="logoimg" />
           </div>
           <div className="contentContainer">
                <div className="nameText">      {props.name}    </div>
                <div className="commentText">   {props.comment} </div>
           </div>
        </div>
    )
}
