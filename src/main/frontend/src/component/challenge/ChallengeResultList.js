import React, {useState,useEffect} from 'react'
import axios from 'axios'
import Container from '@mui/material/Container';
import { DataGrid, GridColDef } from '@mui/x-data-grid';
import { useSearchParams  } from 'react-router-dom';


export default function ChallengeResultList(props) {

    const columns: GridColDef[] = [
        { field: 'mno',        headerName: '회원번호',    width: 70 },
        { field: 'chno',      headerName: '챌린지번호',    width: 100},
        { field: 'cdate',      headerName: '등록시간',    width: 100},
        {
            field: 'uuidFile',
            headerName: '이미지',
            width: 150,
            editable: true,
            renderCell: (params) => <img style={{width:'150px'}} src={"http://localhost:8080/static/media/"+params.value} />, // renderCell will render the component
        },
        {
            field: 'sstate',
            headerName: '상태',
            width: 50,
            editable: true,
            renderCell: (params) => params.value===0 ? '미확인' : '확인',// renderCell will render the component
        },
    ]

    const [ searchParams , setSearchParams ]  = useSearchParams();

    // 1. 상태변수
    const [ rows , setRows ] = useState ([]);

    // 2. 제품 호출 axios
    const getChallenge = () => {axios.get('/challenge/results/admin',{ params : searchParams })
        .then( r => {
            console.log(r);
            setRows(r.data)
        })}
    // 3. 컴포넌트 생명주기에 따른 함수 호출
    useEffect (()=>{ getChallenge();},[])
    // 4.
    const [rowSelectionModel, setRowSelectionModel] = React.useState([]);

    const onDeleteHandler = () => {
        console.log("onDeleteHandler");
        let msg = window.confirm(" 정말 확인하시겠습니까? 복구가 불가능 합니다.")
        if( msg === true ){ // 확인 선택시
            // 선택된 글귀 하나씩 서버에 전달
            rowSelectionModel.forEach( r => {
                console.log(r)

                axios.put("/challenge/results", { 'sno' : r , "sstate" : 1})
                    .then( (r)=>{getChallenge();})
            })
        }
    }

    return (<Container>
        <div style={{marginTop:'30px'}}>
            <button type='button'
                onClick ={ onDeleteHandler }
                disabled={ rowSelectionModel.length === 0 ? true : false }
                >
                    확인완료
            </button>
            <div style={{ height: 600, width: '100%' , backgroundColor:'white' }}>
                <DataGrid rowHeight={100}
                    getRowId = {(row) => row.sno}
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
    </Container>)
}