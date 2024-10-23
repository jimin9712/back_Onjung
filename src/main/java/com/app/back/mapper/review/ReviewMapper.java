package com.app.back.mapper.review;

import com.app.back.domain.post.Pagination;
import com.app.back.domain.review.ReviewDTO;
import com.app.back.domain.review.ReviewVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface ReviewMapper {
    // 추가
    public void insert(ReviewVO reviewVO);
    // 조회
    public Optional<ReviewVO> selectById(Long id);
    // 전체 조회
    public List<ReviewDTO> selectAll(Pagination pagination);
    // 전체 개수
    public int selectCount();
    // 수정
    public void update(ReviewDTO reviewDTO);
    // 삭제
    void deleteById(Long id);
}