import React, {useState, useEffect} from 'react'
import axios from 'axios'
import Container from '@mui/material/Container';
import { styled } from '@mui/material/styles';
import { Table , TableBody , TableContainer , TableHead , TableRow , Paper , IconButton } from '@mui/material';
import TableCell, { tableCellClasses } from '@mui/material/TableCell';
import RateReviewTwoToneIcon from '@mui/icons-material/RateReviewTwoTone';


export default function SimriTest(props) {
    const [ test , setTest ] = useState([]);
    let count = 1;

    useEffect(() => {
        axios.get('/simritest').then(r =>{ console.log(r.data); setTest(r.data) })
    }, [])

    // table css
    const StyledTableCell = styled(TableCell)(({ theme }) => ({
      [`&.${tableCellClasses.head}`]: {
        backgroundColor: theme.palette.common.black,
        color: theme.palette.common.white,
      },
    }));
    // table css
    const StyledTableRow = styled(TableRow)(({ theme }) => ({
      '&:nth-of-type(odd)': {
        backgroundColor: theme.palette.action.hover,
      }
    }));


    return (
        <Container>
            <TableContainer sx={{ mt: 5 }} component={Paper}>
                <Table sx={{ Width: '100%' }} aria-label="customized table">
                    <TableHead>
                        <TableRow sx={{ backgroundColor : "rgb(220, 190, 112)"}}>
                            <TableCell sx={{ color : "white" }} align="left">번호</TableCell>
                            <TableCell sx={{ color : "white" }} align="center">테스트종류</TableCell>
                            <TableCell sx={{ color : "white" }} align="center">참여하기</TableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {test.map((t) => (
                            <StyledTableRow>
                                <StyledTableCell align="left">{count++}</StyledTableCell>
                                <StyledTableCell align="center">{t.stitle}</StyledTableCell>
                                <StyledTableCell align="center">
                                    <IconButton onClick={() => { window.open(t.surl); }}>
                                        <RateReviewTwoToneIcon />
                                    </IconButton>
                                </StyledTableCell>
                            </StyledTableRow>
                        ))}
                    </TableBody>
                </Table>
            </TableContainer>
        </Container>
      );
}