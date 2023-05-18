import React, { useState, useEffect, useRef } from 'react';
import axios from 'axios';

export default function KakaoMap(props) {
    const mapContainer =useRef(null);
    const { kakao } = window;
    const mapOption = { center: new kakao.maps.LatLng( 36.2683, 127.6358  ), level: 3 };


    useEffect(()=>{
        var map = new kakao.maps.Map( mapContainer.current , mapOption);

        // 주소-좌표 변환 객체를 생성합니다.
        const geocoder = new kakao.maps.services.Geocoder();

        // 주소로 좌표를 검색합니다..
        geocoder.addressSearch( props.haddr , function (result, status) {

            // 정상적으로 검색이 완료됐으면
            if (status === kakao.maps.services.Status.OK) {

                var coords = new kakao.maps.LatLng(result[0].y, result[0].x);

                // 결과값으로 받은 위치를 마커로 표시합니다
                var marker = new kakao.maps.Marker({ map: map, position: coords });

                // 인포윈도우로 장소에 대한 설명을 표시합니다
                var infowindow = new kakao.maps.InfoWindow({
                    content: '<div style="width:150px;color:#DCBE70;text-align:center;padding:6px 0;"> '+ props.hname +' </div>'
                });
                infowindow.open(map, marker);

                // 지도의 중심을 결과값으로 받은 위치로 이동시킵니다
                map.setCenter(coords);
            }
        })
    })



    return(<>
        <div id="map" ref={ mapContainer } style={{width:'100%', height: '400px'}} >  </div>
    </>)
}