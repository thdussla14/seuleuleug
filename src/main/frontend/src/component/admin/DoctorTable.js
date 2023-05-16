import React, {useState,useEffect} from 'react'
import axios from 'axios';
import Box from '@mui/material/Box';
import Button from '@mui/material/Button';
import Modal from '@mui/material/Modal';
import { DataGrid, GridColDef} from '@mui/x-data-grid';
import ConfirmDoctor from './ConfirmDoctor';

export default function DoctorTable(props) {

    // 모달 이벤트
    const [open, setOpen] = React.useState(false);
    const handleOpen = () => setOpen(true);
    const handleClose = () => setOpen(false);

    const columns: GridColDef[] = [
        { field: 'hmno',    headerName: '번호',       width: 50 ,type: 'number'},
        { field: 'hmname',  headerName: '의사명',      width: 100},
        { field: 'hmphone', headerName: '전화번호',    width: 150},
        { field: 'hmstate', headerName: '승인',       width: 100 ,
             renderCell : (params)=>(
                 <strong>
                     { params.value === 1 ? '승인완료'
                     : <ConfirmDoctor  item={params} />
                     }
                 </strong>)
        }
    ];

    // 1. 상태변수
    const item = props.item;
    console.log(item);
    const [ rows , setRows ] = useState ([]);

    // 3. 컴포넌트 생명주기에 따른 함수 호출
    useEffect (()=>{ setRows(item.row.list)},[])
    // 4.
    const [rowSelectionModel, setRowSelectionModel] = React.useState([]);

    // 의사 삭제
    const onDeleteHandler = () => {
        console.log("onDeleteHandler");
        let msg = window.confirm(" 정말 삭제하시겠습니까? 복구가 불가능 합니다.")
        if( msg == true ){ // 삭제 확인 선택시
            // 선택된 글귀 하나씩 서버에 전달
            rowSelectionModel.forEach( r => {
                axios.delete("/hmember", {params : { "hmno" : r }})
                    .then( (r)=> {console.log(r); alert('삭제 완료'); window.location.reload();} );
            })
        }
    }

    // css
    const style = {
      position: 'absolute',
      top: '50%',
      left: '50%',
      transform: 'translate(-50%, -50%)',
      width: '90%',
      height: '300px',
      bgcolor: 'background.paper',
      border: '2px solid #000',
      boxShadow: 24,
      p: 4,
    };

    return (
    <div>
      <Button onClick={handleOpen} style={{color:'black'}}> 소속의사 </Button>
      <Modal
        open={open}
        onClose={handleClose}
        aria-labelledby="modal-modal-title"
        aria-describedby="modal-modal-description"
      >
        <Box sx={style}>
            <div style={{height: '90%'}}>
                <DataGrid
                  getRowId = {(row) => row.hmno}
                  rows={rows}
                  columns={columns}
                  initialState={{
                    pagination: {
                      paginationModel: { page: 0, pageSize: 5 },
                    },
                  }}
                  pageSizeOptions={[5, 10]}
                  checkboxSelection
                  onRowSelectionModelChange={(newRowSelectionModel) => {
                    setRowSelectionModel(newRowSelectionModel);
                  }}
                />
                <Button variant="contained" onClick ={ onDeleteHandler } disabled={ rowSelectionModel.length == 0 ? true : false }
                    style={{height:'30px', margin:'10px', backgroundColor: '#DCBE70'}}>
                    선택 삭제
                </Button>
            </div>
        </Box>
      </Modal>
    </div>
    );
}
