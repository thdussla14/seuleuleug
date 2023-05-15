package seuleuleug.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import seuleuleug.domain.hospital.HospitalDto;
import seuleuleug.domain.hospital.HospitalEntity;
import seuleuleug.domain.hospital.HospitalEntityRepository;
import seuleuleug.domain.hospital.PageDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class HospitalService {
    @Autowired
    private HospitalEntityRepository hospitalEntityRepository;

    // 출력
    public PageDto get(PageDto pageDto) {
        Pageable pageable = PageRequest.of(pageDto.getPage()-1,10, Sort.by(Sort.Direction.DESC, "halliance"));
        Page<HospitalEntity> entityPage = hospitalEntityRepository.findBySearch( pageDto.getKey() , pageDto.getKeyword() , pageable);
        List<HospitalDto> hospitalDtoList = new ArrayList<>();
        entityPage.forEach((h)->{hospitalDtoList.add(h.toDto());});

        return PageDto.builder()
                .hospitalDtoList(hospitalDtoList)
                .totalCount(entityPage.getTotalElements())
                .totalPage(entityPage.getTotalPages())
                .page(pageDto.getPage())
                .build();
    }

    //제휴 병원리스트 출력
    public List<HospitalDto> joinList ( ) {
        List<HospitalEntity> list = hospitalEntityRepository.findAll();
        List<HospitalDto> DtoList = new ArrayList<>();
        if(list.size()>0){
            list.forEach((H)->{
                DtoList.add(H.toDto());
            });
            return DtoList;
        }
        return null;
    }

    // 검색 병원 출력
    public List<HospitalDto> findhospital (String keyword ) {
        List<HospitalEntity> list = hospitalEntityRepository.findBykeyword(keyword);
        List<HospitalDto> DtoList = new ArrayList<>();
        if(list.size()>0){
            list.forEach((H)->{ DtoList.add(H.toDto());});
            return DtoList;
        }
        return null;
    }




}
