import React from 'react';
import Comment from './Comment'; // 컴포넌트 호출

export default function CommentList(){

    useEffect(()=>{
        axios.get('/board')
            .then(  r => { console.log(r);})
            .catch( e => { console.log(e);})
    }, [])

    return (
        <div>
            {
                r.map((c)=>{
                    return(<Comment name={c.name} comment={c.comment} />)
                })
            }
        </div>
    )
}