import React, { useState , useEffect , useReducer } from 'react';
import axios from 'axios';
import Hospital from './Hospital';
// ----------------------------------------------------------------
import Container from '@mui/material/Container';
import { Paper, Box, Stack, styled, Typography, Pagination, IconButton } from '@mui/material';
import { Input, InputLabel, InputAdornment } from '@mui/material';
import { FormControl, MenuItem, Select } from '@mui/material';
import SvgIcon from "@mui/material/SvgIcon";
import { SvgIconComponent } from "@mui/icons-material";
import LocationOnIcon from '@mui/icons-material/LocationOn';
import SearchIcon from '@mui/icons-material/Search';



export default function HospitalList( props ) {

    let [ hospital , setHospital ] = useState( [] );
    let [ pageInfo , setPageInfo ] = useState( { 'page' : 1 , 'key' : '' , 'keyword' : '' } );
    let [ totalPage , setTotalPage ] = useState( 1 );
    let [ totalCount , setTotalCount ] = useState( 1 );
    const [selected, setSelected] = useState("");


    // 서버에게 요청하기[ 컴포넌트가 처음 생성 되었을때 ]
    useEffect( ()=>{
        axios.get("/hospital/list" , { params: pageInfo } )
             .then( r =>{
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

    // 검색
    const onSearch = ()=>{
        pageInfo.key = selected
        pageInfo.keyword = document.querySelector('#keyword').value
        pageInfo.page = 1 // 검색했을때 첫페이지 이동
        setPageInfo( {...pageInfo} );
    }

    let handleChange = (event: SelectChangeEvent) => {
        setSelected(event.target.value);
    };


    return(<>
        <Container>
            <Box sx={{ display: 'flex', alignItems: 'center' }} >
                <div style={{ width: '30%'}}>
                    <FormControl fullWidth sx={{ mt : 1 }} size="small">
                        <Select id="key" onChange={handleChange}>
                            <MenuItem value="hname">병원명</MenuItem>
                            <MenuItem value="haddr">주소</MenuItem>
                            <MenuItem value="hnum">전화번호</MenuItem>
                        </Select>
                    </FormControl>
                </div>
                <div style={{ width: '70%'}}>
                    <FormControl fullWidth sx={{ m: 1 }} variant="standard">
                        <InputLabel>Search</InputLabel>
                        <Input type="text" id="keyword" endAdornment={
                            <InputAdornment position="end">
                                <IconButton onClick={ onSearch }>
                                    <SearchIcon />
                                </IconButton>
                            </InputAdornment>}
                        />
                    </FormControl>
                </div>
            </Box>
            {
               hospital.map((h)=>(
                        <Hospital item ={ h }  />
                       ))
            }
            <div style={{ display : 'flex', justifyContent : 'center', margin : '40px 0px' }}>
                <Pagination count={ totalPage } color="primary" onChange={ selectPage } />
            </div>
        </Container>
    </>)
}