import React, {useState, useEffect , useRef} from 'react'
import axios from 'axios'
import Container from '@mui/material/Container';
import Grid from '@mui/material/Grid';
import Box from '@mui/material/Box';
import Typography from '@mui/material/Typography';
import Skeleton from '@mui/material/Skeleton';
import Pagination from '@mui/material/Pagination';

let data = [ ];

interface MediaProps {
    loading?: boolean;
}

function detail(){
    window.location.href = '/challenge/challengedetail'
}

function Media(props: MediaProps) {
    const { loading = false } = props;

    return (
        <Grid container wrap="wrap">
            {(loading ? Array.from(new Array(3)) : data).map((item, index) => (
                <Box key={index} sx={{ width: "48%", marginRight: "1%", marginLeft: "1%", marginBottom:"0px" ,my: 5 }} onClick={detail} >
                    {item ? (
                        <img
                            style={{ width: "100%", height: 118 }}
                            alt={item.title}
                            src={item.src}
                        />
                    ) : (
                        <Skeleton variant="rectangular" width={"100%"} height={118} />
                    )}
                    {item ? (
                        <Box sx={{ pr: 2 }}>
                            <Typography gutterBottom variant="body2">
                                {item.title}
                            </Typography>
                            <Typography display="block" variant="caption" color="text.secondary">
                                {item.channel}
                            </Typography>
                            <Typography variant="caption" color="text.secondary">
                                {`${item.views} • ${item.createdAt}`}
                            </Typography>
                        </Box>
                    ) : (
                        <Box sx={{ pt: 0.5 }}>
                            <Skeleton />
                            <Skeleton width="100%" />
                        </Box>
                    )}
                </Box>
            ))}
        </Grid>
    );
}


export default function Challenge(props) {
    // 1. 상태변수
    let [ rows , setRows ] = useState([]);
    let [ pageInfo,setPageInfo] = useState({'page':1});
    let [ totalPage, setTotalPage ] = useState(1);
    let [ totalCount, setTotalCount ] = useState(1);
    // 2. 제품호출 axios 함수
    useEffect( ()=>{
        axios.get('/challenge',{ params : pageInfo })
            .then( r => { console.log(r);
                data = [];
                r.data.challengesDtoList.forEach((i)=>{
                let datas = {
                                src: "http://localhost:8080/static/media/"+i.chfiles[0].uuidFile,
                                title: i.chname,
                                channel: "오늘부터 시작",
                                views: "참여인원",
                                createdAt: i.cdate,
                            }
                data.push(datas);
            })
            console.log(data)
            setRows( r.data.challengesDtoList )
            setTotalPage(r.data.totalPage)
            setTotalCount(r.data.totalCount)
            } )
            .catch( err => { console.log(err); })
    } , [pageInfo] )

    // 3. 페이징 번호 선택
    const selectPage = (e,value) =>{
        //console.log(e.target.outerText); // tag 밖에 있는 text 출력
        console.log(value);
        pageInfo.page=value;
        setPageInfo({...pageInfo})
    }

    console.log( rows );



    return (<>
        <Box sx={{ overflow: 'hidden' }}>
            <Media />
        </Box>
        <div style={{display:'flex',justifyContent:'center',margin:'40px 0px'}}>
            <Pagination count={totalPage} color="primary" onChange={selectPage} />
        </div>
    </>)
}