import React, {useState,useEffect} from 'react'
import axios from 'axios'
import Container from '@mui/material/Container';
import Button from '@mui/material/Button';
import { DataGrid, GridColDef, GridValueGetterParams, GridRowSelectionModel } from '@mui/x-data-grid';


export default function HospitalTable(props) {

    const columns: GridColDef[] = [
      { field: 'hno',            headerName: '번호',       width: 50 ,type: 'number'},
      { field: 'hname',          headerName: '병원명',     width: 100},
      { field: 'hahallianceddr', headerName: '제휴',       width: 50}
    ];

    // 1. 상태변수
    const [ rows , setRows ] = useState ([]);
    // 2. 제품 호출 axios
    const getHList = () => {axios.get("/hospital/alllist").then( r => {console.log(r); setRows(r.data)})}
    // 3. 컴포넌트 생명주기에 따른 함수 호출
    useEffect (()=>{ getHList();},[])
    // 4.
    const [rowSelectionModel, setRowSelectionModel] = React.useState([]);

    return (<>
        <div style={{marginTop:'30px'}}>
            <div style={{ height: 400, width: '100%', backgroundColor:'white' }}>
            <DataGrid
              getRowId = {(row) => row.hno}
              rows={rows}
              columns={columns}
              initialState={{
                pagination: {
                  paginationModel: { page: 0, pageSize: 5 },
                },
              }}
              pageSizeOptions={[5, 10]}
              onRowSelectionModelChange={(newRowSelectionModel) => {
                setRowSelectionModel(newRowSelectionModel);
              }}
            />
            </div>
        </div>
    </>)
}