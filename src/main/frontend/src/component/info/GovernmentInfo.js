import React, {useState, useEffect} from 'react'
import axios from 'axios'
import Container from '@mui/material/Container';
import styles from '../../css/GovernmentInfo.css';

export default function GovernmentInfo(props) {

    const [govInfo, setGovInfo] = useState([])
    const [govInfoList, setGovInfoList] = useState([])

    const getGovInfo = () => {
        axios.get('/crawling')
        .then(r => {
            console.log(r.data)
            setGovInfo(r.data)
            setGovInfoList(r.data)
        })
        .catch(e => console.log(e));
    }

    useEffect(() => {
        getGovInfo()
    }, [])



    const choice = (e,no) => {
        let govArray = [];
        if(no==='00'){
            setGovInfoList([...govInfo])
        }else {
            govInfo.forEach(o=>{
                if(o.card_body3.split('/')[1].split('-')[0].replace(" ","")===no){
                    govArray.push(o)
                }
            })
            setGovInfoList([...govArray])
        }
        console.log(govInfoList)
    }

    return (<>
        <container maxWidth="md">
            <div className="serchBox">
                <div class="tab-menu">
                    <button type="button" class="tab-list " onClick={(e)=>choice(e,'00')}>전체보기</button>
                    <button type="button" class="tab-list " onClick={(e)=>choice(e,'02')}>서울특별시</button>
                    <button type="button" class="tab-list " onClick={(e)=>choice(e,'051')}>부산광역시</button>
                    <button type="button" class="tab-list " onClick={(e)=>choice(e,'053')}>대구광역시</button>
                    <button type="button" class="tab-list " onClick={(e)=>choice(e,'032')}>인천광역시</button>
                    <button type="button" class="tab-list " onClick={(e)=>choice(e,'062')}>광주광역시</button>
                    <button type="button" class="tab-list " onClick={(e)=>choice(e,'042')}>대전광역시</button>
                    <button type="button" class="tab-list " onClick={(e)=>choice(e,'052')}>울산광역시</button>
                    <button type="button" class="tab-list " onClick={(e)=>choice(e,'031')}>경기도</button>
                    <button type="button" class="tab-list " onClick={(e)=>choice(e,'033')}>강원도</button>
                    <button type="button" class="tab-list " onClick={(e)=>choice(e,'043')}>충청북도</button>
                    <button type="button" class="tab-list " onClick={(e)=>choice(e,'041')}>충청남도</button>
                    <button type="button" class="tab-list " onClick={(e)=>choice(e,'063')}>전라북도</button>
                    <button type="button" class="tab-list " onClick={(e)=>choice(e,'061')}>전라남도</button>
                    <button type="button" class="tab-list " onClick={(e)=>choice(e,'054')}>경상북도</button>
                    <button type="button" class="tab-list " onClick={(e)=>choice(e,'055')}>경상남도</button>
                    <button type="button" class="tab-list " onClick={(e)=>choice(e,'064')}>제주특별자치도</button>
                </div>

            </div>
        </container>
        <Container maxWidth="md">
            {govInfoList.map((row) => (
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