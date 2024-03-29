import React, {useState,useEffect} from 'react'
import axios from 'axios'
import { DataGrid, GridColDef } from '@mui/x-data-grid';

export default function ChallengeTable(props) {

    const columns: GridColDef[] = [
        { field: 'chno',        headerName: '번호',           width: 50 ,type: 'number'},
        { field: 'chname',      headerName: '챌린지이름',    width: 100},
        { field: 'chcontent',      headerName: '챌린지내용',    width: 100},
        {
            field: "Print",
            renderCell: (cellValues) => {
                return (
                    <button
                        variant="contained"
                        color="primary"
                        onClick={(event) => {
                            handleClick(event, cellValues);
                        }}
                    >
                        Print
                    </button>
                );
            }
        },
    ]

    // 1. 상태변수
    const [ rows , setRows ] = useState ([]);

    // 2. 제품 호출 axios
    const getChallenge = () => {axios.get('/challenge/admin')
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
        let msg = window.confirm(" 정말 삭제하시겠습니까? 복구가 불가능 합니다.")
        if( msg === true ){ // 삭제 확인 선택시
            // 선택된 글귀 하나씩 서버에 전달
            rowSelectionModel.forEach( r => {
                axios.delete("/challenge", {params : { chno : r }})
                    .then( (r)=>{getChallenge();})
            })
        }
    }
    console.log(rows)

    const handleClick = (event, cellValues) => {
        console.log(cellValues)
        window.location.href = '/challenge/challengeResultList?chno='+cellValues.id
    }

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
                    getRowId = {(row) => row.chno}
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