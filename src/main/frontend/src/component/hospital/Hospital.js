import React , { useState , useEffect , useReducer } from 'react';
import axios from 'axios';
import KakaoMap from './KakaoMap';

import Container from '@mui/material/Container';
import { Paper, Box, Stack, styled, Typography, Pagination, IconButton } from '@mui/material';
import { Input, InputLabel, InputAdornment } from '@mui/material';
import { FormControl, MenuItem, Select } from '@mui/material';
import SvgIcon from "@mui/material/SvgIcon";
import { SvgIconComponent } from "@mui/icons-material";
import LocationOnIcon from '@mui/icons-material/LocationOn';
import SearchIcon from '@mui/icons-material/Search';


export default function Hospital(props){
    // 병원
    const [ hospital , setHospital ] = useState( props.item );

    // 2. 컴포넌트 실행시 한번
    useEffect( ()=>{ setHospital(props.item);
        if(display == "block"){ toggleDisplay(); }
     }, [props.item])

    // 지도 클릭 display
    const [display, toggleDisplay] = useReducer( (e) => (e === "block" ? "none" : "block"), "none" );

    // 리스트 css
    const Item = styled(Paper)(({ theme }) => ({
        backgroundColor: theme.palette.mode === 'dark' ? '#1A2027' : '#fff',
        ...theme.typography.body2,
        padding: theme.spacing(1),
        color: theme.palette.text.secondary
    }));

    return ( <>
          <Item sx={{ my: 2, mx: 'auto', p: 2, display: 'flex', justifyContent: 'space-between' }}>
          <Stack>
            <Typography variant="h5">
              <a style={ hospital.hurl == null ? { textDecoration: 'none' } : { textDecoration: 'underline' }} onClick={() => { hospital.hurl != null && window.open('http://' + hospital.hurl); }}>
                {hospital.hname}
              </a>
            </Typography>
            <Typography>주소 : {hospital.haddr}</Typography>
            <Typography variant="body2">전화번호 : {hospital.hnum}</Typography>
          </Stack>
          <IconButton onClick={toggleDisplay}>
            <SvgIcon fontSize="large" component={LocationOnIcon} inheritViewBox />
          </IconButton>
        </Item>
        <div style={{ display }}>
            <Item sx={{ my: 3, mx: 'auto', p: 2 }}>
                <KakaoMap haddr={hospital.haddr} hname={hospital.hname} />
            </Item>
        </div>
    </>)
}