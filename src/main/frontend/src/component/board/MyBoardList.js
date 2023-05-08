import React, {useState,useEffect} from 'react'
import { useSearchParams  } from 'react-router-dom';
import axios from 'axios'
import Container from '@mui/material/Container';
import { Paper , Stack , styled , Typography } from '@mui/material';

export default function MyBoardList(props) {

    console.log('MyBoardList')
    const [ searchParams , setSearchParams ]  = useSearchParams();
    useEffect(()=>{
         axios.get('/board/mylist',  { params : { bemail : searchParams.get("bemail")}})
             .then(  (r) => { console.log(r);})
             .catch( (e) => { console.log(e);})
    }, [])

    return (<>
       <Container>
            <div> 내글 목록 </div>

        </Container>
    </>)

}