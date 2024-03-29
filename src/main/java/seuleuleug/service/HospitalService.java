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

import javax.transaction.Transactional;
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
        List<HospitalEntity> list = hospitalEntityRepository.findjoinList();
        List<HospitalDto> DtoList = new ArrayList<>();
        if(list.size()>0){
            list.forEach((H)->{DtoList.add(H.toDto());});
            return DtoList;
        }
        return null;
    }

    // 전체 병원 출력
    public List<HospitalDto> alllist ( ) {
        List<HospitalEntity> list = hospitalEntityRepository.findAll();
        List<HospitalDto> DtoList = new ArrayList<>();
        if(list.size()>0){
            list.forEach((H)->{ DtoList.add(H.toDto());});
            return DtoList;
        }
        return null;
    }

    // 병원 제휴 등록
    @Transactional
    public boolean changestate(HospitalDto hospitalDto){
        Optional<HospitalEntity> optionalHospitalEntity = hospitalEntityRepository.findById(hospitalDto.getHno());
        if(optionalHospitalEntity.isPresent()){
            optionalHospitalEntity.get().setHalliance(1);
            return true;
        }
        return false;
    }

}
