import React, {useState, useEffect} from 'react'
import axios from 'axios'
import Container from '@mui/material/Container';
import TextField from '@mui/material/TextField';
import Button from '@mui/material/Button';
import styles from '../../css/main.css';
import CategoryList from './CategoryList';

export default function Write(props) {
    // 3. 카테고리 변경
    let [ cno, setCno ] = useState(0);
    const categoryChange = (cno) => {
        console.log(cno)
        setCno(cno);
    }
    // 지역은 select 로 변경 => 도 / 시  2개 선택 정보 묶어서 저장
    const setBoard = () =>{
        let info = {
            cno       : cno,
            blegion   : document.querySelector('#blegion').value,
            bemail    : document.querySelector('#bemail').value,
            bpassword : document.querySelector('#bpassword').value,
            btitle    : document.querySelector('#btitle').value,
            bcontent  : document.querySelector('#bcontent').value
        }
        console.log(info);
        axios.post("/board/bwrite",info)
            .then((r) => {
                console.log(r);
                 if(r.data===1){alert('카테고리가 존재하지 않습니다.') }
                 else if(r.data===3){alert('게시물 등록에 실패하였습니다.') }
                 else if(r.data===4){alert('게시물이 등록되었습니다.'); window.location.href="/" }
                 }
            )
    }

    const back = () => {
        window.location.href="/"
    }

    return(<Container>
        <h3>  게시글 작성 페이지 </h3>
        <CategoryList categoryChange= {categoryChange} />
        <TextField fullWidth className="blegion"   id="blegion"    label="지역"      variant="standard" /> <br />
        <TextField fullWidth className="bemail"    id="bemail"     label="이메일"    variant="standard" /> <br />
        <TextField fullWidth className="bpassword" id="bpassword"  label="비밀번호"  variant="standard" /> <br />
        <TextField fullWidth className="btitle"    id="btitle"     label="제목"     variant="standard" /> <br />
        <TextField fullWidth style={{marginTop:'20px'}}
          className="bcontent"
          id="bcontent"
          label="내용"
          multiline
          rows={10}
          variant="standard"
        />
        <div style={{marginTop:'20px'}}>
            <Button style={{marginRight:'10px'}} variant="outlined" onClick={setBoard}> 등록 </Button>
            <Button variant="outlined" onClick={back} > 취소 </Button>
        </div>
    </Container>)
}