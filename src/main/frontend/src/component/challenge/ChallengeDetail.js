import React, {useState, useEffect , useRef} from 'react'
import { useSearchParams  } from 'react-router-dom';
import axios from 'axios'
import Container from '@mui/material/Container';
import ImageList from '@mui/material/ImageList';
import ImageListItem from '@mui/material/ImageListItem';
import styles from '../../css/Challenge.css';

    let today = new Date();

    let year = today.getFullYear();
    let month = ('0' + (today.getMonth() + 1)).slice(-2);
    let day = ('0' + today.getDate()).slice(-2);

    let dateString = year + '-' + month  + '-' + day;

    console.log(dateString);

export default function ChallengeDetail(props) {
    // 1.
    const writeForm = useRef(null);
    let [login,setLogin] = useState(sessionStorage.getItem("email"));
    let [itemData,setItemData] = useState([]);
    let itemDatas = []
    const [ searchParams , setSearchParams ]  = useSearchParams();
    const [ items , setItems ] = useState([]);
    const [ imgs , setImgs ] = useState();
    const [ itemByMno , SetItemByMno ] = useState([]);

    useEffect(()=>{
        get();
        getChallenge();
        getChallengeByMno();
    }, [searchParams])

    const get=()=>{
        axios.get('/challenge/detail',{ params : searchParams })
            .then(  (r) => {
                console.log(r);
                setItems(r.data);
                r.data.chfiles.forEach(e=>{
                    console.log(e.uuidFile)
                    setImgs("http://localhost:8080/static/media/"+e.uuidFile)
                })
            })
            .catch( (e) => { console.log(e);})
    }

    // 인증 등록
    const postResult = () =>{
        console.log(writeForm.current)
        const writeFormData = new FormData( writeForm.current );
        console.log(writeFormData)
        axios.post( '/challenge/results' , writeFormData )
            .then( r=>{
                if( r.data == true ){
                    alert('등록성공');
                    window.location.reload();
                }
                else{ alert('등록실패'); }
            })
            .catch( (e) => { console.log(e);})
    }

    // 2. 오늘 참여한 사람들
    const getChallenge = () => {axios.get('/challenge/results',{ params : searchParams })
        .then( r => {
            console.log(r);
            itemData =[]
            r.data.forEach(e=>{
                let item = {
                    img: "http://localhost:8080/static/media/"+e.uuidFile,
                    title: e.sno,
                }
                itemDatas.push(item)
                console.log(itemDatas)
            })
            setItemData( [...itemDatas] )
        })
        .catch( (e) => { console.log(e);})
    }

    // 로그인한 사람 인증 목록
    const getChallengeByMno = () => { console.log('실행'+searchParams.get('chno')+','+login)
    axios.get('/challenge/results/info',{ chno:searchParams.get('chno'), memail:login })
        .then( r => {
            console.log(r);
            SetItemByMno(r.data)
            console.log(itemByMno)
        })
        .catch( (e) => { console.log(e);})
    }

    return(<Container>
        <div className="divs">
            <img style={{ width:'300px', marginTop:'30px' }} src={imgs} name="chimg"     id="chimg" />
            <div name="chname"     id="chname">{items.chname}</div>
            <div name="chcontent"     id="chcontent">{items.chcontent}</div>
        </div>
        {login=='null'?
            <div>로그인하고 참여하기</div>
            :

                <form ref={ writeForm }>
                    참여하기 : <input type="file" name="simg" id="simg"  /> <br />
                    <input type="hidden" value={searchParams.get("chno")} name="chno" id="chno" />
                    <input type="hidden" value={login} name="memail" id="memail" />
                    <button type="button" onClick={postResult}>제출</button>
                </form>
        }
        <div>인증 사진</div>
        <ImageList  cols={3} rowHeight={120}>
            {itemData.map((item) => (
                <ImageListItem key={item.img}>
                <img
                    src={`${item.img}?w=120&h=120&fit=crop&auto=format`}
                    srcSet={`${item.img}?w=120&h=120&fit=crop&auto=format&dpr=2 2x`}
                    alt={item.title}
                    loading="lazy"
                />
                </ImageListItem>
            ))}
        </ImageList>

    </Container>)
}