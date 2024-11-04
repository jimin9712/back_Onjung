package com.app.back.domain.support;



import com.app.back.domain.attachment.AttachmentVO;
import com.app.back.domain.post.PostVO;
import com.app.back.domain.volunteer.Pagination;
import com.app.back.domain.volunteer.VolunteerVO;
import com.app.back.enums.PostType;
import lombok.*;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Component
@Getter @Setter @ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
public class SupportDTO {
    private Long postId;
    private String postTitle;
    private String postContent;
    private String postSummary;
    private String postType;
    private String postStatus;
    private Long postViewCount;
    private String createdDate;
    private String updatedDate;

    private Long memberId;
    private String memberNickName;

    private String attachmentFileName;
    private String attachmentFilePath;
    private String attachmentFileSize;
    private String attachmentFileType;

    private Long id;
    private int goalPoint;
    private String supportSDate;
    private String supportEDate;

    private long daysLeft;

    private String postTypeDisplayName;

    //    게시글 정보 가져오기
    public PostVO toPostVO() {
        return new PostVO(id, postTitle, postContent, postSummary, postType, postStatus, postViewCount, memberId, createdDate, updatedDate);
    }

    //    첨부파일 정보 가져오기
    public AttachmentVO toAttachmentVO() {
        return new AttachmentVO(id, attachmentFileName, attachmentFilePath, attachmentFileSize, attachmentFileType, postId, createdDate);
    }

    // 봉사게시글 정보 가져오기
    public SupportVO toVO() {
        return new SupportVO(id, goalPoint, supportSDate, supportEDate);
    }


    // 봉사 모집 기간 남은일수 계산
    public void calculateDaysLeft() {
        if (supportEDate != null) {
            try {
                // 종료일을 LocalDate로 변환 (예: yyyy-MM-dd 형식)
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate endDate = LocalDate.parse(supportEDate, formatter); // 종료일을 LocalDate로 변환
                LocalDate today = LocalDate.now();
                // 종료일이 오늘보다 미래일 경우에만 남은 일수 계산
                if (today.isBefore(endDate) || today.isEqual(endDate)) {
                    this.daysLeft = ChronoUnit.DAYS.between(today, endDate);
                    System.out.println("남은 일수: " + this.daysLeft); // 디버깅용 출력
                    System.out.println("작성날짜:" + this.createdDate);   //디버깅용 작성날짜 출력
                } else {
                    this.daysLeft = 0; // 종료일이 지났으면 0으로 설정
                    System.out.println("종료일이 지났습니다.");
                }
            } catch (DateTimeParseException e) {
                System.err.println("Invalid date format for vtEDate: " + supportEDate);
                this.daysLeft = 0;
            }
        } else {
            this.daysLeft = 0;
            System.out.println("종료일(vtEDate)이 설정되지 않았습니다.");
        }
    }

    // postType 값을 사용해 displayName을 설정하는 메서드
    public void setPostType(String postType) {
        this.postType = postType;

        // Enum에서 한글 이름을 가져와 설정
        try {
            PostType type = PostType.valueOf(postType);
            this.postTypeDisplayName = type.getDisplayName();
        } catch (IllegalArgumentException e) {
            this.postTypeDisplayName = "알 수 없는 타입"; // Enum에 없는 값인 경우 기본값 설정
        }
    }

    @Data
    @AllArgsConstructor
    public class VolunteerResponseDTO {
        private List<SupportDTO> lists;
        private Pagination pagination;
    }

}


