import React, {useState, useEffect} from 'react'
import axios from 'axios'
import Container from '@mui/material/Container';
import TextField from '@mui/material/TextField';
import Button from '@mui/material/Button';
import styles from '../../css/main.css';

export default function ChallengeWrite(props) {
    // 3. 카테고리 변경
    let [ cno, setCno ] = useState(0);
    const categoryChange = (cno) => {
        console.log(cno)
        setCno(cno);
    }
    // 지역은 select 로 변경 => 도 / 시  2개 선택 정보 묶어서 저장
    const setBoard = () =>{
        let info = {
            chname       : cno,
            chcontent    : document.querySelector('#legion').value,
            chimg     : document.querySelector('#email').value
        }
        console.log(info);
        axios.post("/challenge",info)
            .then((r) => {
                console.log(r);
                if(r.data===1){alert('카테고리가 존재하지 않습니다.') }
                else if(r.data===3){alert('게시물 등록에 실패하였습니다.') }
                else if(r.data===4){alert('게시물이 등록되었습니다.'); window.location.href="/" }
                }
            )
    }

    return(<Container>
        <h3>  게시글 작성 페이지 </h3>

        <TextField fullWidth className="chname"    id="chname"     label="제목"      variant="standard" /> <br />
        <TextField fullWidth style={{marginTop:'20px'}}
            className="chcontent"
            id="chcontent"
            label="내용"
            multiline
            rows={10}
            variant="standard"
        /> <br />
        <input type="file" className="chimg"     id="chimg"      label="사진"    variant="standard" /> <br />
        <div style={{marginTop:'20px'}}>
            <Button style={{marginRight:'10px'}} variant="outlined" onClick={setBoard}> 등록 </Button>
            <Button variant="outlined"> 취소 </Button>
        </div>
    </Container>)
}