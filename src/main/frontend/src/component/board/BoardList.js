import React, {useState,useEffect} from 'react'
import { useSearchParams  } from 'react-router-dom';
import axios from 'axios'
import Container from '@mui/material/Container';
import { Paper , Stack , styled } from '@mui/material';
import CheckPasswordModal from './CheckPasswordModal';

export default function BoardList(props) {

    console.log('BoardList')
    // 1. 해당 이메일로 작성된 게시글 목록 출력
    const [ items , setItems ] = useState([]);
    useEffect(()=>{
         axios.get('/board/alllist')
             .then(  (r) => { console.log(r); setItems(r.data);})
             .catch( (e) => { console.log(e);})
    }, [])
    // 2. 게시글 선택 이벤트 -> 게시글번호 파라미터값으로 가지고 상세보기 페이지로 전환
    const Handler = (e) => {
        console.log(e.target.value)
        console.log(e.target);
        console.log(e.target.text);
        window.location.href = "/board/doctor/hboard?bno="+e.target.value
    }
    // css
    const Item = styled(Paper)(({ theme }) => ({
      backgroundColor: theme.palette.mode === 'dark' ? '#1A2027' : '#fff',
      ...theme.typography.body2,
      padding: theme.spacing(1),
      textAlign: 'center',
      color: theme.palette.text.secondary,
    }));

    return (<>
       <Container>
            <h3 style={{textAlign:'center'}}> 고민글 목록 </h3>
            <Stack spacing={2}>
                {items.map((i)=>(
                    <Item>
                       <h3> {i.btitle}   </h3>
                       <button value={i.bno} onClick={Handler} > detail </button>
                    </Item>
                ))}
            </Stack>
        </Container>
    </>)

}