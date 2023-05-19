import React, { useState , useEffect , useReducer } from 'react';
import axios from 'axios';
import Hospital from './Hospital';
// ----------------------------------------------------------------
import Container from '@mui/material/Container';
import { Box, Pagination, IconButton } from '@mui/material';
import { Input, InputLabel, InputAdornment } from '@mui/material';
import { FormControl, MenuItem, Select } from '@mui/material';
import SearchIcon from '@mui/icons-material/Search';



export default function HospitalList( props ) {

    const [ hospital , setHospital ] = useState( [] );
    const [ pageInfo , setPageInfo ] = useState( { 'page' : 1 , 'key' : '' , 'keyword' : '' } );
    const [ totalPage , setTotalPage ] = useState( 1 );
    const [ totalCount , setTotalCount ] = useState( 1 );
    const [ selected , setSelected ] = useState("");

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

    // 제휴 구분 css
    const styleCss = {
        border: '1px solid #DCBE70',
        padding: '0 20px',
        marginTop: '25px',
        borderRadius: '10px',
        borderWidth: '2px'
    }

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
            <div style={ styleCss }>
            { hospital.filter((i)=> i.halliance == 1 ).map((h)=>( <Hospital item ={ h } /> )) }
            </div>
            { hospital.filter((i)=> i.halliance == 0 ).map((h)=>( <Hospital item ={ h } /> )) }
            <div style={{ display : 'flex', justifyContent : 'center', margin : '40px 0px' }}>
                <Pagination count={ totalPage } color="primary" onChange={ selectPage } />
            </div>
        </Container>
    </>)
}