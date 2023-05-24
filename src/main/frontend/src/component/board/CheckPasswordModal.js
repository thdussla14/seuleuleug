import * as React from 'react';
import axios from 'axios';
import Box from '@mui/material/Box';
import Button from '@mui/material/Button';
import Typography from '@mui/material/Typography';
import TextField from '@mui/material/TextField';
import Modal from '@mui/material/Modal';
import { Paper , Stack , styled  } from '@mui/material';

export default function CheckPasswordModal(props) {

    // 모달 이벤트
    const [open, setOpen] = React.useState(false);
    const handleOpen = () => setOpen(true);
    const handleClose = () => setOpen(false);

    // 선택된 게시물 정보 전달받기
    const item = props.item
    // 입력된 비밀번호와 게시물 비밀번호 비교
    const checkPassword = (e) =>{
     item.bpassword = document.querySelector('#password').value; console.log(item);
     axios.post('/board/checkpw',  item )
         .then((r) => {
             if(r.data === true){window.location.href="/board/myboard?bno="+item.bno;}
             else{alert('비밀번호가 일치하지 않습니다.'); window.location.href='/'}
         })
    }
    // css
    const style = {
      position: 'absolute',
      top: '50%',
      left: '50%',
      transform: 'translate(-50%, -50%)',
      width: 300,
      bgcolor: 'background.paper',
      border: '2px solid #000',
      boxShadow: 24,
      p: 4,
    };
    //css
    const Item = styled(Paper)(({ theme }) => ({
      backgroundColor: theme.palette.mode === 'dark' ? '#1A2027' : '#fff',
      ...theme.typography.body2,
      padding: theme.spacing(1),
      textAlign: 'center',
      color: theme.palette.text.secondary,
    }));
  return (
   <Item onClick={handleOpen}>
     <div>
        <h3 > {item.btitle} </h3>
        <Modal
        open={open}
        onClose={handleClose}
        aria-labelledby="modal-modal-title"
        aria-describedby="modal-modal-description"
        >
            <Box sx={style}>
              <Typography id="modal-modal-title" variant="h6" component="h2">
                Password CHECK
              </Typography>
              <Typography id="modal-modal-description" sx={{ mt: 2 }}>
               <TextField id="password" label="password" variant="outlined" className ="password"/>
                           <Button variant="contained" onClick={checkPassword}
                               style={{height:'56px', marginLeft:'10px', backgroundColor: '#DCBE70'}}> 확인 </Button>
              </Typography>
            </Box>
        </Modal>
     </div>
   </Item>
  );
}
