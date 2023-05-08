import React, {useState, useEffect} from 'react'
import axios from 'axios'
import Container from '@mui/material/Container';
import TextField from '@mui/material/TextField';
import Button from '@mui/material/Button';
import styles from '../../css/GovernmentInfo.css';

export default function GovernmentInfo(props) {

    const [govInfo, setGovInfo] = useState([])

    const getGovInfo = () => {
        axios.get('http://localhost:8080/crawling')
        .then(r => {
            console.log(r.data)
            setGovInfo(r.data)
        })
        .catch(e => console.log(e));
    }

    useEffect(() => {
        getGovInfo()
    }, [])

    return (<>
        <Container maxWidth="md">
            {govInfo.map((row) => (
                <div class="card-box">
                    <div class="card-tag">
                        <em class="chip">{row.card_tag}</em>
                    </div>
                    <div class="card-head">
                        <a href={row.card_head_href} class="card-title">{row.card_head}</a>
                        <p class="card-desc">{row.card_head_info}</p>
                    </div>
                    <div class="card-body">
                        <ul class="card-cont">
                            <li class="card-list">
                                <strong class="card-sub">신청기간 :</strong>
                                <span class="card-text"> {row.card_body1}</span>
                            </li>
                            <li class="card-list">
                                <strong class="card-sub">접수기관 :</strong>
                                <span class="card-text"> {row.card_body2}</span>
                            </li>
                            <li class="card-list">
                                <strong class="card-sub">전화문의 :</strong>
                                <span class="card-text"> {row.card_body3}</span>
                            </li>
                            <li class="card-list">
                                <strong class="card-sub">지원형태 :</strong>
                                <span class="card-text"> {row.card_body4}</span>
                            </li>
                            <li class="card-list">
                                <strong class="card-sub">신청방법 :</strong>
                                <span class="card-text"> {row.card_body5}</span>
                            </li>
                        </ul>
                    </div>
                </div>
            ))}
        </Container>
    </>)
}