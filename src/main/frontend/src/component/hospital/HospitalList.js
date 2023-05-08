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
    let [ pageInfo , setPageInfo ] = useState( { 'page' : 1 , 'key' : '' , 'keyword' : '' } );
    let [ totalPage , setTotalPage ] = useState( 1 );
    let [ totalCount , setTotalCount ] = useState( 1 );

    // 서버에게 요청하기[ 컴포넌트가 처음 생성 되었을때 ]
    useEffect( ()=>{
        axios.get("/hospital/list" , { params: pageInfo } )
             .then( r =>{ console.log(r);
                setHospital( r.data.hospitalDtoList );
                setTotalCount( r.data.totalCount );
                setTotalPage( r.data.totalPage );
             })
             .catch( err =>{ console.log(err);})
    } , [pageInfo])

    // 페이징 번호 선택
    const selectPage = (e , value)=>{
        console.log( value );
        pageInfo.page = value;
        setPageInfo( {...pageInfo} );
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

    // 검색
    const onSearch = ()=>{
        pageInfo.key = document.querySelector('.key').value
        pageInfo.keyword = document.querySelector('.keyword').value
        pageInfo.page = 1 // 검색했을때 첫페이지 이동
        setPageInfo( {...pageInfo} );
    }



    return(<>
        <Container>
            <div style={{ display : 'flex', justifyContent : 'center', margin : '40px 0px' }} className="searchBox">
                <select className="key">
                    <option value="hname"> 병원명 </option>
                    <option value="haddr"> 주소 </option>
                    <option value="hnum"> 전화번호 </option>
                </select>
                <input type="text" className="keyword" />
                <button type="button" onClick={ onSearch } > 검색 </button>
            </div>
            { Hospitals }
            <div style={{ display : 'flex', justifyContent : 'center', margin : '40px 0px' }}>
                <Pagination count={ totalPage } color="primary" onClick={ selectPage } />
            </div>
        </Container>
    </>)
}