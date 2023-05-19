import React, {useState,useEffect} from 'react'
import { useSearchParams  } from 'react-router-dom';
import axios from 'axios'
import Card from '@mui/material/Card';
import Chip from '@mui/material/Chip';
import CardActions from '@mui/material/CardActions';
import CardContent from '@mui/material/CardContent';
import Button from '@mui/material/Button';
import Typography from '@mui/material/Typography';
import CommentList from './CommentList';

export default function MyBoard(props) {

    console.log('MyBoard')
    // 선택한 게시물 번호 파라미터방법으로 전달받기
    const [ searchParams , setSearchParams ]  = useSearchParams();
    // 해당 게시물 정보 가져오기
    const [ item , setItem ] = useState({});
    useEffect(()=>{
         console.log(searchParams)
         console.log(searchParams.get("bno"))
         axios.get('/board/detail',{ params : { bno : searchParams.get("bno")}})
             .then(  r => { setItem(r.data)})
             .catch( e => { console.log(e);})
     }, [])
    // 해당 게시물 삭제
    const deletemy = ( ) => {
         axios.delete('/board',{ params : { bno : searchParams.get("bno")}})
             .then(  r => { console.log(r);
                        if(r.data === true){
                            alert('해당 고민글이 삭제되었습니다.'); window.location.href="/"
                        }
                        else{alert('삭제 실패하였습니다.')}
                    }
             )
    }

    return (<>
        <Card sx={{ minWidth: 275 }}>
          <CardContent>
            <Typography sx={{ fontSize: 14 }} color="text.secondary" gutterBottom>
                <Chip label={item.cname} />
            </Typography>
            <Typography variant="h5" component="div" style={{margin: '10px'}}>
              {item.btitle}
            </Typography>
            <Typography variant="body2" style={{height:'200px', margin: '10px'}}>
              {item.bcontent}
            </Typography>
          </CardContent>
          <CardActions>
            <Button size="small" onClick={deletemy}> 삭제 </Button>
          </CardActions>
        </Card>
        <div>
            <CommentList bno={ searchParams.get("bno")} />
        </div>
    </>)

}