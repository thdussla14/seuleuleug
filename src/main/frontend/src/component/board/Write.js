import React, {useState} from 'react'
import axios from 'axios'
import Container from '@mui/material/Container';
import TextField from '@mui/material/TextField';
import Button from '@mui/material/Button';
import CategoryList from './CategoryList';
import InputLabel from '@mui/material/InputLabel';
import MenuItem from '@mui/material/MenuItem';
import FormControl from '@mui/material/FormControl';
import Select from '@mui/material/Select';
import Box from '@mui/material/Box';

export default function Write(props) {
    // 카테고리 변경
    let [ cno, setCno ] = useState(0);
    const categoryChange = (cno) => {
        console.log(cno)
        setCno(cno);
    }
    const [ local, setLocal ] = useState(0);
    const handleChange = (event) => {
      setLocal(event.target.value);
      console.log(event.target.value)
    };
    // 지역은 select 로 변경 => 도 / 시  2개 선택 정보 묶어서 저장
    const setBoard = () =>{
        let info = {
            cno       : cno,
            blegion   : local,
            bemail    : document.querySelector('#bemail').value,
            bpassword : document.querySelector('#bpassword').value,
            btitle    : document.querySelector('#btitle').value,
            bcontent  : document.querySelector('#bcontent').value
        }
        console.log(info);
        axios.post("/board/bwrite",info)
            .then((r) => {
                console.log(r);
                 if(r.data===1){alert('카테고리가 존재하지 않습니다.') }
                 else if(r.data===3){alert('게시물 등록에 실패하였습니다.') }
                 else if(r.data===4){alert('게시물이 등록되었습니다.'); window.location.href="/" }
                 }
            )
    }
    // 취소 버튼 클릭시 Main으로 전환
    const back = () => {
        window.location.href="/"
    }

    return(<Container>
        <h3>  게시글 작성 페이지 </h3>
        <div style={{ display: 'flex' }}>
            <CategoryList categoryChange= {categoryChange} />
            <Box sx={{ minWidth: 100 }}>
              <FormControl style={{width:'200px', margin:'20px'}} size="small">
                <InputLabel id="demo-simple-select-label"> Local </InputLabel>
                <Select value={local} label="지역" onChange={handleChange}>
                    <MenuItem value={"서울 특별시"}>서울 특별시  </MenuItem>
                    <MenuItem value={"부산 특별시"}>부산 광역시  </MenuItem>
                    <MenuItem value={"인천 특별시"}>인천 광역시  </MenuItem>
                    <MenuItem value={"대구 특별시"}>대구 광역시  </MenuItem>
                    <MenuItem value={"광주 특별시"}>광주 광역시  </MenuItem>
                    <MenuItem value={"대전 특별시"}>대전 광역시  </MenuItem>
                    <MenuItem value={"울산 특별시"}>울산 광역시  </MenuItem>
                    <MenuItem value={"경기도"}>    경기도      </MenuItem>
                    <MenuItem value={"강원도"}>    강원도      </MenuItem>
                    <MenuItem value={"충청남도"}>   충청남도     </MenuItem>
                    <MenuItem value={"충청북도"}>   충청북도     </MenuItem>
                    <MenuItem value={"전라남도"}>   전라남도     </MenuItem>
                    <MenuItem value={"전라북도"}>   전라북도     </MenuItem>
                    <MenuItem value={"경상남도"}>   경상남도     </MenuItem>
                    <MenuItem value={"경상북도"}>   경상북도     </MenuItem>
                    <MenuItem value={"제주특별자치도"}>제주특별자치도</MenuItem>
                </Select>
              </FormControl>
            </Box>
        </div>
        <TextField fullWidth className="bemail"    id="bemail"     label="이메일"    variant="standard" /> <br />
        <TextField fullWidth className="bpassword" id="bpassword"  label="비밀번호"  variant="standard" /> <br />
        <TextField fullWidth className="btitle"    id="btitle"     label="제목"     variant="standard" /> <br />
        <TextField fullWidth style={{marginTop:'20px'}}
          className="bcontent"
          id="bcontent"
          label="내용"
          multiline
          rows={10}
          variant="standard"
        />
        <div style={{marginTop:'20px'}}>
            <Button style={{marginRight:'10px'}} variant="outlined" onClick={setBoard}> 등록 </Button>
            <Button variant="outlined" onClick={back} > 취소 </Button>
        </div>
    </Container>)
}