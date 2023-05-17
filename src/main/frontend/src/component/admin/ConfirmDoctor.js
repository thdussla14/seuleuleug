import React, {useState,useEffect} from 'react';
import axios from 'axios';
import Box from '@mui/material/Box';
import Modal from '@mui/material/Modal';
import Button from '@mui/material/Button';


export default function ChildModal(props) {

    const [open, setOpen] = React.useState(false);
    const handleOpen = () => {
    setOpen(true);
    };
    const handleClose = () => {
    setOpen(false);
    };

    const style = {
      position: 'absolute',
      top: '50%',
      left: '50%',
      transform: 'translate(-50%, -50%)',
      width: '250px' ,
      bgcolor: 'background.paper',
      border: '2px solid #000',
      boxShadow: 24,
      pt: 2,
      px: 4,
      pb: 3,
    };

    // 의사 정보 전달 받기
    const item = props.item;
    console.log(item);

    // 의사 등록 승인
    const onupdate = (e) => {
        console.log(e);
        console.log(e.target.value);
        axios.put("/hmember/hupdate",  { "hmno" : e.target.value } )
            .then( (r)=> {console.log(r.data) ; alert('승인 완료'); window.location.href="/admin/dashboard"});
    }

    return (
    <React.Fragment>
      <Button
          onClick={handleOpen}
          style={{height:'15px', margin:'10px 0px',textDecoration: 'none',
                 backgroundColor: '#DCBE70', color:'white'}}>
          요청
      </Button>
      <Modal
        open={open}
        onClose={handleClose}
        aria-labelledby="child-modal-title"
        aria-describedby="child-modal-description"
      >
        <Box sx={style}>
          <img style={{width:'200px',height:'200px'}} src={'http://localhost:8080/static/media/'+item.row.hmcertification} />
          <Button
              value={item.id}
              onClick={onupdate}
              style={{height:'15px', margin:'10px 0px',textDecoration: 'none',
              backgroundColor: '#DCBE70', color:'white'}}>
              승인
          </Button>
          < Button
              onClick={handleClose}
              style={{height:'15px', margin:'10px 0px',textDecoration: 'none',
              backgroundColor: '#DCBE70', color:'white'}} >
              Close
          </Button>
        </Box>
      </Modal>
    </React.Fragment>
    );
}