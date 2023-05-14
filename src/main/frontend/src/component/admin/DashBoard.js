import React from 'react'
import axios from 'axios'
import Container from '@mui/material/Container';
import TextField from '@mui/material/TextField';
import Button from '@mui/material/Button';
import Box from '@mui/material/Box';
import Tab from '@mui/material/Tab';
import TabContext from '@mui/lab/TabContext';
import TabList from '@mui/lab/TabList';
import TabPanel from '@mui/lab/TabPanel';
import WordTable from './WordTable';
import CategoryTable from './CategoryTable';
import ChallengeTable from './ChallengeTable'

export default function DashBoard(props) {
    // 탭 전환
    const [value, setValue] = React.useState('1');
    const handleChange = (event, newValue) => {
        setValue(newValue);
    }
    // 게시글 카테고리 등록
    const setCategory = () =>{
        let cname = document.querySelector('#cname');
        console.log(cname.value)
        axios.post("/board/category/write", {"cname": cname.value})
            .then((r) =>  {
                if(r.data === true)
                    {console.log(r); alert('카테고리 등록 성공했습니다.'); cname.value='';
                     window.location.reload();}
            })
    }

    // 응원글귀 등록
    const setWord = () =>{
        let wcontent = document.querySelector('#wcontent');
        console.log(wcontent.value)
        axios.post("/word", {"wcontent": wcontent.value})
            .then((r) => {
                if(r.data === true)
                    {console.log(r); alert('응원글귀 등록 성공했습니다.'); wcontent.value='';
                     window.location.reload();}
            })
    }

    //챌린지 등록
    const setChallenge = () =>{
        window.location.href = '/challenge/challengewrite'
    }

    return(<Container>
            <h3>  관리자 페이지 </h3>
            <Box sx={{ width: '100%', typography: 'body1' }}>
              <TabContext value={value}>
                <Box sx={{ borderBottom: 1, borderColor: 'divider' }}>
                  <TabList onChange={handleChange} aria-label="lab API tabs example">
                    <Tab label="카테고리"   value="1" />
                    <Tab label="응원글귀"   value="2" />
                    <Tab label="제휴병원"   value="3" />
                    <Tab label="챌린지"     value="4" />
                  </TabList>
                </Box>
                <TabPanel value="1">
                    <TextField id="cname" label="카테고리명" variant="outlined" className ="cname"/>
                    <Button variant="contained" onClick={setCategory}
                        style={{height:'56px', marginLeft:'10px', backgroundColor: '#DCBE70'}}> 카테고리 등록 </Button> <br/>

                    <CategoryTable />
                </TabPanel>
                <TabPanel value="2">
                    <TextField id="wcontent" label="응원글귀" variant="outlined" className ="wcontent"/>
                    <Button variant="contained" onClick={setWord}
                        style={{height:'56px', marginLeft:'10px', backgroundColor: '#DCBE70'}}> 응원글귀 등록 </Button> <br/>

                    <WordTable />
                </TabPanel>
                <TabPanel value="3">
                     <Button variant="contained" onClick={setWord}
                                            style={{height:'56px', marginLeft:'10px', backgroundColor: '#DCBE70'}}> 제휴병원 등록 </Button>
                     <Button variant="contained" onClick={setWord}
                                            style={{height:'56px', marginLeft:'10px', backgroundColor: '#DCBE70'}}> 제휴의사 등록 </Button> <br/>
                    <h3> 제휴병원 목록 </h3>
                    <h3> 제휴의사 목록 </h3>
                </TabPanel>
                <TabPanel value="4">
                     <Button variant="contained" onClick={setChallenge}
                                            style={{height:'56px', marginLeft:'10px', backgroundColor: '#DCBE70'}}> 챌린지 등록 </Button> <br/>
                    <ChallengeTable />
                </TabPanel>
              </TabContext>
            </Box>
    </Container>)
}