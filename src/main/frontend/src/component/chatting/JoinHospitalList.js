import React, {useState,useEffect} from 'react'
import axios from 'axios'

import Box from '@mui/material/Box';
import InputLabel from '@mui/material/InputLabel';
import MenuItem from '@mui/material/MenuItem';
import FormControl from '@mui/material/FormControl';
import Select from '@mui/material/Select';

export default function JoinHospitalList(props) {

    // 1. 제휴병원 리스트
    let [ list, setList ] = useState([]); // {} 객체 , [] 배열/리스트
    useEffect(()=>{
        axios.get("/hospital/joinlist")
            .then( r => {console.log(r); setList(r.data);})
            .catch( e => { console.log(e);})
    }, [])

    // 2. 선택 카테고리 변경 이벤트
    const [ hospital, setHospital ] = useState(0);
    const handleChange = (event) => {
      setHospital(event.target.value);
      props.hospitalChange(event.target.value);
    };

    return(<>
        <Box sx={{ minWidth: 100 }}>
          <FormControl style={{width:'200px'}} size="small">
            <InputLabel id="demo-simple-select-label"> Hospital </InputLabel>
            <Select value={hospital} label="소속병원" onChange={handleChange}>
            <MenuItem value={0}> 전체보기 </MenuItem>
            {list.map((h)=>(<MenuItem key={h.hno} value={h.hno}> {h.hname} </MenuItem>))}
            </Select>
          </FormControl>
        </Box>
    </>);
}