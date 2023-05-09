import React from 'react';
import Container from '@mui/material/Container';
import { Paper , Stack , styled , Typography } from '@mui/material';
//import LocationOnIcon from '@mui/icons-material/LocationOn';
//import SvgIconComponent from "@mui/icons-material";


export default function HospitalList( props ) {

    // 리스트 css
    const Item = styled(Paper)(({ theme }) => ({
        backgroundColor: theme.palette.mode === 'dark' ? '#1A2027' : '#fff',
        ...theme.typography.body2,
        padding: theme.spacing(1),
        color: theme.palette.text.secondary
    }));


    return(<>
        <Container>
            <Item sx={{ my: 3, mx: 'auto', p: 2, }} >
                <Stack>
                    <Typography variant="h5">영경의료재단 전주병원</Typography>
                    <Typography>주소 : 전북 전주시 완산구 한두평 3길 13</Typography>
                    <Typography variant="body2">전화번호 : 063-220-7200</Typography>
                </Stack>
                {/*<SvgIcon component={LocationOnIcon} inheritViewBox />*/}
            </Item>
            <Item sx={{ my: 3, mx: 'auto', p: 2, }} >
                <Stack>
                    <Typography variant="h5">영경의료재단 전주병원</Typography>
                    <Typography>주소 : 전북 전주시 완산구 한두평 3길 13</Typography>
                    <Typography variant="body2">전화번호 : 063-220-7200</Typography>
                </Stack>
            </Item>
            <Item sx={{ my: 3, mx: 'auto', p: 2, }} >
                <Stack>
                    <Typography variant="h5">영경의료재단 전주병원</Typography>
                    <Typography>주소 : 전북 전주시 완산구 한두평 3길 13</Typography>
                    <Typography variant="body2">전화번호 : 063-220-7200</Typography>
                </Stack>
            </Item>
        </Container>
    </>)
}