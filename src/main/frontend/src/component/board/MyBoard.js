import React, {useState,useEffect} from 'react'
import axios from 'axios'

export default function MyBoard(props) {

    useEffect(()=>{
         axios.get('/board')
             .then(  r => { console.log(r);})
             .catch( e => { console.log(e);})
     }, [])

    return {<>

    </>}

}