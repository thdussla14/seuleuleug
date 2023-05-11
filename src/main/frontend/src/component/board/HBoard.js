import React, {useState,useEffect} from 'react'
import { useSearchParams  } from 'react-router-dom';
import axios from 'axios'
import Container from '@mui/material/Container';
import Box from '@mui/material/Box';
import Card from '@mui/material/Card';
import CardActions from '@mui/material/CardActions';
import CardContent from '@mui/material/CardContent';
import Button from '@mui/material/Button';
import Typography from '@mui/material/Typography';
import Comwrite from './Comwrite';

export default function MyBoard(props) {

    console.log('HBoard')
    // 선택한 게시물 번호 파라미터방법으로 전달받기
    const [ searchParams , setSearchParams ]  = useSearchParams();
    // 해당 게시물 정보 가져오기
    const [ item , setItem ] = useState({});
    useEffect(()=>{
         console.log(searchParams)
         console.log(searchParams.get("bno"))
         axios.get('/board/detail',{ params : { bno : searchParams.get("bno")}})
             .then(  r => { console.log(r); setItem(r.data)})
             .catch( e => { console.log(e);})
     }, [])

    return (
        <Container>
            <Card sx={{ minWidth: 275 }}>
              <CardContent>
                <Typography sx={{ fontSize: 14 }} color="text.secondary" gutterBottom>
                  {item.cname}
                </Typography>
                <Typography variant="h5" component="div">
                  {item.btitle}
                </Typography>
                <Typography variant="body2" style={{height:'200px'}}>
                  {item.bcontent}
                </Typography>
              </CardContent>
              <CardActions>
              </CardActions>
            </Card>
            <Comwrite bno={item.bno}/>
        </Container>
    )

}