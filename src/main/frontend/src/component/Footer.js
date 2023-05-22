import React  from 'react';
import {Box,AppBar,Toolbar}from '@mui/material';

export default function Footer(props) {

    return (<>
        <Box sx={{ flexGrow: 1 ,position: 'absolute', bottom: 0, width: '100%'}}>
          <AppBar position="static" style={{ backgroundColor: "#DCBE70" }}>
            <Toolbar>

            </Toolbar>
          </AppBar>
        </Box>
    </>)

}