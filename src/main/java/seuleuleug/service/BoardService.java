package seuleuleug.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import seuleuleug.domain.board.*;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class BoardService {
    @Autowired
    CategoryEntityRepository categoryEntityRepository;
    @Autowired
    BoardEntityRepository boardEntityRepository;


    // 카테고리 등록
    public boolean writeCategory(CategoryDto categoryDto){
        log.info("writeCategory"+categoryDto);
        categoryEntityRepository.save(categoryDto.toCategoryEntity());
        return false;
    }

    // 모든 카테고리 출력
    public List<CategoryDto> getCategoryList() {
        List<CategoryEntity> categoryEntityList =  categoryEntityRepository.findAll();
        List<CategoryDto> list = new ArrayList<>();
        categoryEntityList.forEach((e) -> {
            list.add(e.toCategoryDto());
        });
        return list;
    }
    // 글작성
    @Transactional
    public byte writeBoard(BoardDto boardDto){
        log.info("writeBoard"+boardDto);

        // 카테고리 번호를 이용한 카테고리 entity 찾기
        Optional<CategoryEntity> optionalCategoryEntity =
                categoryEntityRepository.findById(boardDto.getCno());
        if(!optionalCategoryEntity.isPresent()){return 1;}
        CategoryEntity categoryEntity = optionalCategoryEntity.get();

        // 게시물 저장
        BoardEntity boardEntity = boardEntityRepository.save(boardDto.toboardEntity());
        boardEntity.setCategoryEntity(categoryEntity);
        return 4;
    }
    // 이메일 확인
    public boolean checkemail(String bemail){
        List<BoardEntity> boardEntities = boardEntityRepository.findByBemail(bemail);
        if(boardEntities.size()>0){ return true;}
        return false;
    }
    // 내가 쓴 게시물 목록 출력
    public List<BoardDto> getMyBoardList(String bemail){
        log.info("myboardList"+bemail);
        List<BoardEntity> boardEntities = boardEntityRepository.findByBemail(bemail);
        List<BoardDto> myboardList = new ArrayList<>();
        if(boardEntities.size()>0){
            boardEntities.forEach((b)->{
                myboardList.add(b.toBoardDto());
            });
            log.info("myboardList"+myboardList);
            return myboardList;}
        return null;
    }


}
