import React, {useState,useEffect} from 'react'
import { useSearchParams  } from 'react-router-dom';
import axios from 'axios'
import Container from '@mui/material/Container';
import { Paper , Stack , styled  } from '@mui/material';
import CheckPasswordModal from './CheckPasswordModal';

export default function MyBoardList(props) {

    console.log('MyBoardList')
    // checkEmail에서 입력한 이메일 파라미터방법으로 전달받기
    const [ searchParams , setSearchParams ]  = useSearchParams();
    // 해당 이메일로 작성된 게시글 목록 출력
    const [ items , setItems ] = useState([]);
    useEffect(()=>{
         axios.get('/board/mylist',  { params : { bemail : searchParams.get("bemail")}})
             .then(  (r) => { console.log(r);
                if(r.data == ""){alert('해당 이메일로 등록한 게시물이 없습니다.'); window.location.href='/';}
                setItems(r.data); })
             .catch( (e) => { console.log(e);})
    }, [])

    return (<>
       <Container>
            <h3 style={{textAlign:'center'}}> 내글 목록 </h3>
            <Stack spacing={2}>
                {items.map((i)=>(<CheckPasswordModal item={i} />))}
            </Stack>
        </Container>
    </>)

}