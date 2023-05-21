import React, {useRef} from 'react'
import axios from 'axios'
import Container from '@mui/material/Container';
import TextField from '@mui/material/TextField';
import Button from '@mui/material/Button';

export default function ChallengeWrite(props) {
    // 1.
    const writeForm = useRef(null); // useRef() 객체={ current : 데이터/DOM } 반환 / 재랜더링시 제외

    // 2.
    const onWriteHandler = () => {
        const writeFormData = new FormData( writeForm.current );
        axios.post( '/challenge' , writeFormData ).then( r=>{
            if( r.data === true ){
                alert('등록성공');
                window.location.href = '/admin/dashboard'
            }
            else{ alert('등록실패'); }
        })
    }

    return(<Container>
        <h3>  챌린지 작성 </h3>
        <form ref={ writeForm }>
            <TextField fullWidth name="chname"    id="chname"     label="제목"      variant="standard" /> <br />
            <TextField fullWidth style={{marginTop:'20px'}}
                name="chcontent"
                id="chcontent"
                label="내용"
                multiline
                rows={10}
                variant="standard"
            /> <br />
            <input type="file" name="chimg"     id="chimg"      label="사진"    variant="standard" /> <br />
        </form>
        <div style={{marginTop:'20px'}}>
            <Button style={{marginRight:'10px'}} variant="outlined" onClick={onWriteHandler}> 등록 </Button>
            <Button variant="outlined"> 취소 </Button>
        </div>
    </Container>)
}