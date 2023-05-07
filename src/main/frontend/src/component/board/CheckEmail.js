import React from 'react'
import axios from 'axios'
import Container from '@mui/material/Container';
import TextField from '@mui/material/TextField';
import Button from '@mui/material/Button';


export default function CheckEmail(props) {

    const checkEmail = () =>{
        axios.get('/board', {params : email})
            .then((r) => {console.log(r);})
    }

    return(<Container>
        <div>
            <h3>  EMAIL CHECK </h3>
            <TextField id="email" label="email" variant="outlined" className ="email"/>
            <Button variant="contained"
                style={{height:'56px', marginLeft:'10px'}}> 내글 찾기 </Button>
        </div>
    </Container>)
}