import React, {useState,useEffect} from 'react'
import axios from 'axios'

import Box from '@mui/material/Box';
import InputLabel from '@mui/material/InputLabel';
import MenuItem from '@mui/material/MenuItem';
import FormControl from '@mui/material/FormControl';
import Select from '@mui/material/Select';

export default function CategoryList(props) {
    // 1. 카테고리 리스트
    let [ list, setList ] = useState([]); // {} 객체 , [] 배열/리스트
    useEffect(()=>{
        axios.get("/board/category/list")
            .then( r => {console.log(r); setList(r.data);})
            .catch( e => { console.log(e);})
    }, [])

    // 2. 선택 카테고리 변경 이벤트
    const [ category, setCategory ] = useState(0);
    const handleChange = (event) => {
      setCategory(event.target.value);
      props.categoryChange(event.target.value);
    };

    return(<>
        <Box sx={{ minWidth: 120 }}>
          <FormControl style={{width:'200px', margin:'20px 0px'}}>
            <InputLabel id="demo-simple-select-label"> Category </InputLabel>
            <Select value={category} label="카테고리" onChange={handleChange}>
            <MenuItem value={0}> 전체보기 </MenuItem>
            {list.map((c)=>(<MenuItem key={c.cno} value={c.cno}> {c.cname} </MenuItem>))}
            </Select>
          </FormControl>
        </Box>
    </>)
}