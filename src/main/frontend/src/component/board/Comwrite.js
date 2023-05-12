import React, {useState, useEffect} from 'react'
import axios from 'axios'
import Container from '@mui/material/Container';
import TextField from '@mui/material/TextField';
import Button from '@mui/material/Button';
import Avatar from '@mui/material/Avatar';
import styles from '../../css/Comment.css'; // css 호출
import doctor from '../../doctor.png';  // img 호출

export default function Comwrite(props) {

    // 선택한 게시물 번호 전달받기
    const bno = props.bno
    // 로그인한 의사 정보 가져오기
    const [ doctor , setDoctor ] = useState([]);
    useEffect(()=>{
         let hmemail = sessionStorage.getItem("email");
         axios.get('/hmember/hcomment', {params : {"hmemail":hmemail}} )
             .then(  (r) => { console.log(r); setDoctor(r.data);})
             .catch( (e) => { console.log(e);})
    }, [])

    // 입력한 코멘트 댓글 저장
    const setComment = () =>{

        let info = {
            bno       : bno,
            rcontent  : document.querySelector('#rcontent').value,
            hmno      : doctor.hmno
        }
        console.log(info);
        axios.post("/board/cwrite",info)
            .then((r) => {
                console.log(r);
                 if(r.data===true){alert('답글 등록되었습니다.') }
                 else if(r.data===false){alert('답글 등록에 실패하였습니다.'); window.location.href="/" }
                 }
            )
    }
    // 취소 버튼 클릭시 BoardList로 전환
    const back = () => {
        window.location.href="/board/boardlist"
    }

    return(<Container>
        <div className="wrapper">
           <div>
               <Avatar alt="Remy Sharp" src={doctor} />
           </div>
           <div className="contentContainer">
                <div className="nameText">   {doctor.hmname} 의사   </div>
           </div>
        </div>
        <TextField fullWidth style={{marginTop:'20px'}}
          className="rcontent"
          id="rcontent"
          label="내용"
          multiline
          rows={10}
          variant="standard"
        />
        <div style={{marginTop:'20px'}}>
            <Button style={{marginRight:'10px'}} variant="outlined" onClick={setComment}> 등록 </Button>
            <Button variant="outlined" onClick={back} > 취소 </Button>
        </div>
    </Container>)
}