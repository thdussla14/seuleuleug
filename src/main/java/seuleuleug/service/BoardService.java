package seuleuleug.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import seuleuleug.domain.board.*;
import seuleuleug.domain.fortune.WordEntity;
import seuleuleug.domain.hospital.HMemberEntity;
import seuleuleug.domain.hospital.HMemberRepository;

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
    @Autowired
    CommentEntityRepository commentEntityRepository;
    @Autowired
    HMemberRepository hMemberRepository;

    // 카테고리 등록
    public boolean writeCategory(CategoryDto categoryDto){
        log.info("writeCategory"+categoryDto);
        categoryEntityRepository.save(categoryDto.toCategoryEntity());
        return true;
    }
    // 카테고리 삭제
    public boolean deleteCategory(int cno) {
        Optional<CategoryEntity> optionalCategoryEntity = categoryEntityRepository.findById(cno);
        if(optionalCategoryEntity.isPresent()){
            categoryEntityRepository.delete(optionalCategoryEntity.get());
            return true;
        }
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
        // 비밀번호 암호화
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        boardDto.setBpassword(passwordEncoder.encode(boardDto.getBpassword()));
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
                myboardList.add(b.toBoardtitleDto());
            });
            log.info("myboardList"+myboardList);
            return myboardList;}
        return null;
    }
    // 전체 게시물 목록 출력
    public List<BoardDto> getBoardList(){
        log.info("alllist boardDto");
        List<BoardEntity> boardEntities = boardEntityRepository.findAll();
        List<BoardDto> boardList = new ArrayList<>();
        if(boardEntities.size()>0){
            boardEntities.forEach((b)->{
                boardList.add(b.toBoardtitleDto());
            });
            return boardList;
        }
        return null;
    }
    // 비밀번호 확인
    public boolean checkpw(BoardDto boardDto) {
        log.info("checkpw boardDto"+boardDto);
        Optional<BoardEntity> optionalBoardEntity = boardEntityRepository.findById(boardDto.getBno());
        if(optionalBoardEntity.isPresent()){
            BoardEntity boardEntity = optionalBoardEntity.get();
            // 암호화 된 비밀번호와 입력받은 비밀번호 비교
            if( new BCryptPasswordEncoder().matches( boardDto.getBpassword() , boardEntity.getBpassword())){return true;}
        }
        return false;
    }
    // 게시물 상세 출력
    public BoardDto getBoard(int bno){
        log.info("detail service"+ bno);
        Optional<BoardEntity> optionalBoardEntity = boardEntityRepository.findById(bno);
        if(optionalBoardEntity.isPresent()){
            return optionalBoardEntity.get().toBoardDto();
        }
        return null;
    }
    //게시물 삭제
    public boolean deletemy(int bno){
        log.info("deletemy service"+ bno);
        Optional<BoardEntity> optionalBoardEntity = boardEntityRepository.findById(bno);
        if(optionalBoardEntity.isPresent()){
            boardEntityRepository.delete(optionalBoardEntity.get());
            return true;
        }
        return false;
    }
    // 답글 작성
    public boolean writecomment(@RequestBody CommentDto commentDto){
        // 게시물 정보
        Optional<BoardEntity> optionalBoardEntity = boardEntityRepository.findById(commentDto.getBno());
            if(optionalBoardEntity.isPresent()){
                // 로그인 정보
                Optional<HMemberEntity> optionalHMemberEntity = hMemberRepository.findById(commentDto.getHmno());
                if(optionalHMemberEntity.isPresent()){
                   CommentEntity entity =  commentDto.toCommentEntity() ;
                    entity.setBoardEntity(optionalBoardEntity.get());
                    entity.setHMemberEntity(optionalHMemberEntity.get());
                    commentEntityRepository.save(entity);
                    optionalBoardEntity.get().getCommentEntityList().add(entity);
                    optionalHMemberEntity.get().getCommentEntityList().add(entity);
                   return true;
                }
            }
        return false;
    }
    // 게시물 답변 출력
    public List<CommentDto> getCommentList(int bno){
        log.info("getCommentList service"+ bno);
        List<CommentEntity> commentEntityList = commentEntityRepository.findByBno(bno);
        List<CommentDto> commentList = new ArrayList<>();
        if(commentEntityList.size()>0){
            commentEntityList.forEach((c)->{
                commentList.add(c.toCommentDto());
            });
            return commentList;
        }
        return null;
    }

}
