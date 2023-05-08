import React from 'react'
import axios from 'axios'
import Container from '@mui/material/Container';
import TextField from '@mui/material/TextField';
import Button from '@mui/material/Button';


export default function CheckEmail(props) {

    const checkEmail = (e) =>{
        const bemail = document.querySelector('#bemail').value
        console.log(bemail)
        axios.get('/board/check', {params : {bemail:bemail}  })
            .then((r) => {
                console.log(r);
                if(r.data === true){window.location.href='/board/myboardlist?bemail='+bemail;}
                else{alert('해당 이메일로 등록한 게시물이 없습니다.'); window.location.href='/'}
            })
    }

    return(<Container>
        <div>
            <h3>  EMAIL CHECK </h3>
            <TextField id="bemail" label="bemail" variant="outlined" className ="bemail"/>
            <Button variant="contained" onClick={checkEmail}
                style={{height:'56px', marginLeft:'10px'}}> 내글 찾기 </Button>
        </div>
    </Container>)

}