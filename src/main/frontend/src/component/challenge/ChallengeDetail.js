import React, {useState, useEffect , useRef} from 'react'
import { useSearchParams  } from 'react-router-dom';
import axios from 'axios'
import Container from '@mui/material/Container';


export default function ChallengeDetail(props) {
    // 0. 파라미터값 받기
    const [ searchParams , setSearchParams ]  = useSearchParams();
    // 1. 글 출력
    const [ items , setItems ] = useState([]);
    const [ imgs , setImgs ] = useState();

    const get=()=>{
        axios.get('/challenge/detail',{ params : searchParams })
             .then(  (r) => {
                 console.log(r);
                 setItems(r.data);
                 r.data.chfiles.forEach(e=>{
                    console.log(e.uuidFile)
                    setImgs("http://localhost:8080/static/media/"+e.uuidFile)
                 })
             })
             .catch( (e) => { console.log(e);})
    }

    useEffect(()=>{get();}, [searchParams])

    return(<>
        <Container>
            <img style={{ width:'300px', marginTop:'30px' }} src={imgs} name="chimg"     id="chimg" />
            <div name="chname"     id="chname">{items.chname}</div>
            <div name="chcontent"     id="chcontent">{items.chcontent}</div>
        </Container>
        <div>
            참여하기 : <input type="file" name="simg" id="simg"  /> <br />
        </div>
    </>)
}