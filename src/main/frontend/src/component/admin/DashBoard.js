import React from 'react'
import axios from 'axios'
import Container from '@mui/material/Container';
import TextField from '@mui/material/TextField';
import Button from '@mui/material/Button';


export default function DashBoard(props) {

    // 응원글귀 등록
    const setWord = () =>{
        let wcontent = document.querySelector('#wcontent');
        console.log(wcontent.value)
        axios.post("/word", {"wcontent": wcontent.value})
            .then((r) => { if(r.data === true) {console.log(r); alert('응원글귀 등록 성공했습니다.'); wcontent.value='';}})
    }

    // 게시글 카테고리 등록
    const setCategory = () =>{
        let cname = document.querySelector('#cname');
        console.log(cname.value)
        axios.post("/board/category/write", {"cname": cname.value})
            .then((r) =>  {if(r.data === true) {console.log(r); alert('카테고리 등록 성공했습니다.'); cname.value='';}})
    }

    return(<Container>

        <h3>  관리자 페이지 </h3>

        <div>
            <h3> 응원글귀 등록 </h3>
            <TextField id="wcontent" label="응원글귀" variant="outlined" className ="wcontent"/>
            <Button variant="contained" onClick={setWord}
                style={{height:'56px', marginLeft:'10px'}}> 응원글귀 등록 </Button>
        </div>

        <div>
            <h3> 카테고리 등록 </h3>
            <TextField id="cname" label="카테고리명" variant="outlined" className ="cname"/>
            <Button variant="contained" onClick={setCategory}
                style={{height:'56px', marginLeft:'10px'}}> 카테고리 등록 </Button>
        </div>

    </Container>)
}