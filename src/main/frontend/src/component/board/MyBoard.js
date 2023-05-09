import React, {useState,useEffect} from 'react'
import { useSearchParams  } from 'react-router-dom';
import axios from 'axios'
import Box from '@mui/material/Box';
import Card from '@mui/material/Card';
import CardActions from '@mui/material/CardActions';
import CardContent from '@mui/material/CardContent';
import Button from '@mui/material/Button';
import Typography from '@mui/material/Typography';
import CommentList from './CommentList';

export default function MyBoard(props) {

    console.log('MyBoard')
    const [ searchParams , setSearchParams ]  = useSearchParams();
    const [ item , setItem ] = useState({});

    useEffect(()=>{
         console.log(searchParams)
         console.log(searchParams.get("bno"))
         axios.get('/board/detail',{ params : { bno : searchParams.get("bno")}})
             .then(  r => { console.log(r); setItem(r.data)})
             .catch( e => { console.log(e);})
     }, [])

    return (<>
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
            <Button size="small"> 삭제 </Button>
          </CardActions>
        </Card>
        <div>
            <CommentList bno={ searchParams.get("bno")} />
        </div>
    </>)

}