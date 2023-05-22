import React, {useState, useEffect , useRef} from 'react'
import { useSearchParams  } from 'react-router-dom';
import axios from 'axios'
import ImageList from '@mui/material/ImageList';
import ImageListItem from '@mui/material/ImageListItem';
import styles from '../../css/Challenge.css';
import PersonIcon from '@mui/icons-material/Person';
import Box from '@mui/material/Box';


let today = new Date();
let year = today.getFullYear();
let month = ('0' + (today.getMonth() + 1)).slice(-2);
let day = ('0' + today.getDate()).slice(-2);
let dateString = year + '-' + month  + '-' + day;

function goLogin(){
    window.location.href = '/member/login'
}

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

    const imageInput = useRef();

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
                if( r.data === true ){
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
        let info = {
            chno:searchParams.get('chno'), memail:login
        }
        axios.get('/challenge/resultsinfo',{params : info})
            .then( r => {
                console.log(r);
                SetItemByMno(r.data)
            })
            .catch( (e) => { console.log(e);})
    }

    const onCickImageUpload = () => {
        imageInput.current.click();
    };

    /*const App = () => {
      // useRef를 이용해 input태그에 접근한다.
        const imageInput = useRef();

      // 버튼클릭시 input태그에 클릭이벤트를 걸어준다.
        const onCickImageUpload = () => {
        imageInput.current.click();
        };

      // input태그는 display:"none" 을 이용해 안보이게 숨겨준다.
        return (
            <>
                <input type="file" style={{ display: "none" }} ref={imageInput} name="simg" id="simg" />
                <button type="button" onClick={onCickImageUpload} className="btn">파일선택</button>
            </>
        );
    };*/

    return(<div>
        <div className="divs">
            <img style={{ width:'100%'}} src={imgs} name="chimg"     id="chimg" />
            <div className="infoBox">
                <div name="chname"     id="chname">{items.chname}</div>
                <div name="count" id="count"><PersonIcon sx={{ fontSize: 17 }} />누적 {items.count}명</div>
                <div name="chcontent"     id="chcontent">{items.chcontent}</div>
            </div>
        </div>
        {login==='null' ?
            <Box className="login" onClick={(e)=>goLogin(e)} >로그인하고 참여하기</Box>
            :
            itemByMno == "" ?
                <form ref={ writeForm } className="login">

                    <input type="file" style={{ display: "none" }} ref={imageInput} name="simg" id="simg" />
                    <button type="button" onClick={onCickImageUpload} className="btn">파일선택</button>
                    <input type="hidden" value={searchParams.get("chno")} name="chno" id="chno" />
                    <input type="hidden" value={login} name="memail" id="memail" />
                    <button type="button" onClick={postResult} className="btn">제출</button>
                </form>
                :
                itemByMno[0].cdate != dateString ?
                    <form ref={ writeForm } className="login">

                        <input type="file" style={{ display: "none" }} ref={imageInput} name="simg" id="simg" />
                        <button type="button" onClick={onCickImageUpload} className="btn">파일선택</button>
                        <input type="hidden" value={searchParams.get("chno")} name="chno" id="chno" />
                        <input type="hidden" value={login} name="memail" id="memail" />
                        <button type="button" onClick={postResult} className="btn">제출</button>
                    </form>
                    :
                    itemByMno[0].sstate == 0 ?
                        <div className="login">
                        {Math.abs(((new Date(itemByMno[0].cdate)).getTime() - (new Date(itemByMno[itemByMno.length-1].cdate)).getTime()) / (1000 * 60 * 60 * 24))+1}
                        일째 도전 중 {itemByMno.length}일 성공
                        오늘 : 참여완료[미인증]</div>
                        :
                        <div className="login">
                        {Math.abs(((new Date(itemByMno[0].cdate)).getTime() - (new Date(itemByMno[itemByMno.length-1].cdate)).getTime()) / (1000 * 60 * 60 * 24))+1}
                        일째 도전 중 {itemByMno.length}일 성공
                        오늘 : 참여완료[인증]</div>
        }
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

    </div>)
}