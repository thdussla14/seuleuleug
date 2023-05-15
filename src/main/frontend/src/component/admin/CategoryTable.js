import React, {useState,useEffect} from 'react'
import axios from 'axios'
import { DataGrid, GridColDef } from '@mui/x-data-grid';

export default function CategoryTable(props) {

    const columns: GridColDef[] = [
      { field: 'cno',        headerName: '번호',           width: 50 ,type: 'number'},
      { field: 'cname',      headerName: '카테고리이름',    width: 200}
    ];

    // 1. 상태변수
    const [ rows , setRows ] = useState ([]);
    // 2. 제품 호출 axios
    const getCategory = () => {axios.get("/board/category/list").then( r => {console.log(r); setRows(r.data)})}
    // 3. 컴포넌트 생명주기에 따른 함수 호출
    useEffect (()=>{ getCategory();},[])
    // 4.
    const [rowSelectionModel, setRowSelectionModel] = React.useState([]);

    const onDeleteHandler = () => {
        console.log("onDeleteHandler");
        let msg = window.confirm(" 정말 삭제하시겠습니까? 복구가 불가능 합니다.")
        if( msg === true ){ // 삭제 확인 선택시
            // 선택된 글귀 하나씩 서버에 전달
            rowSelectionModel.forEach( r => {
                axios.delete("/board/category", {params : { cno : r }})
                    .then( (r)=>{getCategory();})
            })
        }
    }
    console.log(rows)

    return (<>
        <div style={{marginTop:'30px'}}>
            <button type='button'
                onClick ={ onDeleteHandler }
                disabled={ rowSelectionModel.length === 0 ? true : false }
                >
                 선택 삭제
            </button>
            <div style={{ height: 400, width: '100%' , backgroundColor:'white' }}>
              <DataGrid
                getRowId = {(row) => row.cno}
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
            </div>
        </div>
    </>)

}