import React, {useState, useEffect} from 'react'
import axios from 'axios'
import Container from '@mui/material/Container';
import TextField from '@mui/material/TextField';
import Button from '@mui/material/Button';
import styles from '../../css/GovernmentInfo.css';

export default function GovernmentInfo(props) {

    const [govInfo, setGovInfo] = useState(null)

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
            {<div class="card-box">
                <div class="card-tag">
                    <em class="chip">경상남도/거제시</em>
                </div>
                <div class="card-head">
                    <a href="/portal/rcvfvrSvc/dtlEx/537000000202?administOrgCd=ALL" class="card-title" title="서비스 상세페이지 바로가기">정신건강사업 지원</a>
                    <p class="card-desc">- <span class="keyword">정신</span>건강복지센터 운영 - 정신건강사업 운영 - 자살예방 및 생명존중문화 조성</p>
                </div>
                <div class="card-body">
                    <ul class="card-cont">
                        <li class="card-list">
                            <strong class="card-sub">신청기간 :</strong>
                            <span class="card-text"> 상시신청</span>
                        </li>
                        <li class="card-list">
                            <strong class="card-sub">접수기관 :</strong>
                            <span class="card-text"> 보건소</span>
                        </li>
                        <li class="card-list">
                            <strong class="card-sub">전화문의 :</strong>
                            <span class="card-text"> 거제시보건소 정신건강팀 / 055-639-6242</span>
                        </li>
                        <li class="card-list">
                            <strong class="card-sub">지원형태 :</strong>
                            <span class="card-text">현금,현물,기타</span>
                        </li>
                        <li class="card-list">
                            <strong class="card-sub">신청방법 :</strong>
                            <span class="card-text"> 방문 신청</span>
                        </li>
                    </ul>
                </div>
                </div>}
        </Container>
    </>)
}