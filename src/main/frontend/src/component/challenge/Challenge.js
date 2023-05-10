import React, {useState, useEffect , useRef} from 'react'
import axios from 'axios'
import Container from '@mui/material/Container';

export default function Challenge(props) {
    // 1. 상태변수
    const [ rows , setRows ] = useState([]);
    // 2. 제품호출 axios 함수
    const getProduct = () => {
        axios.get("/challenge").then( r => { setRows( r.data ) } )
    }
    // 3. 컴포넌트 생명주기에 따른 함수 호출
    useEffect( () => { getProduct(); } , [] )

    console.log( rows );

    return (<>
        <Container>
            <div>
                아 어떤 방시긍로 뽑지
            </div>
        </Container>
    </>)
}