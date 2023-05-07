import React, { useState , useEffect } from 'react';
import axios from 'axios';
// ----------------------------------------------------------------
import Container from '@mui/material/Container';
import { Paper , Stack , styled , Typography , IconButton } from '@mui/material';
import SvgIcon from "@mui/material/SvgIcon";
import { SvgIconComponent } from "@mui/icons-material";
import LocationOnIcon from '@mui/icons-material/LocationOn';
import Pagination from '@mui/material/Pagination';



export default function HospitalList( props ) {

    let [ hospital , setHospital ] = useState( [] );
    let [ page , setPage ] = useState( 1 );
    let [ totalPage , setTotalPage ] = useState( 1 );
    let [ totalCount , setTotalCount ] = useState( 1 );

    // 서버에게 요청하기[ 컴포넌트가 처음 생성 되었을때 ]
    useEffect( ()=>{
        axios.get("/hospital/list" , { params : { 'page' : page}} )
             .then( r =>{ console.log(r);
                setHospital( r.data.hospitalDtoList );
                setTotalCount( r.data.totalCount );
                setTotalPage( r.data.totalPage );
             })
             .catch( err =>{ console.log(err);})
    } , [page])

    // 페이징 번호 선택
    const selectPage = (e)=>{
        console.log(e.target.outerText);    // 해당 button에서 tag 밖으로 출력되는 text 호출
        page = e.target.outerText
        setPage( page )
    }

    // 리스트 css
    const Item = styled(Paper)(({ theme }) => ({
        backgroundColor: theme.palette.mode === 'dark' ? '#1A2027' : '#fff',
        ...theme.typography.body2,
        padding: theme.spacing(1),
        color: theme.palette.text.secondary
    }));

    // 병원리스트
    const Hospitals = hospital.map((h)=>{return (
         <Item sx={{ my: 3, mx: 'auto', p: 2, display: 'flex' , justifyContent: 'space-between' }} >
            <Stack>
                <Typography variant="h5">{h.hname}</Typography>
                <Typography>주소 : {h.haddr}</Typography>
                <Typography variant="body2">전화번호 : {h.hnum}</Typography>
            </Stack>
            <IconButton>
                <SvgIcon fontSize="large" component={LocationOnIcon} inheritViewBox />
            </IconButton>
        </Item>
    )})




    return(<>
        <Container>
            { Hospitals }
            <div style={{ display : 'flex', justifyContent : 'center', margin : '40px 0px' }}>
                <Pagination count={ totalPage } color="primary" onClick={ selectPage } />
            </div>
        </Container>
    </>)
}