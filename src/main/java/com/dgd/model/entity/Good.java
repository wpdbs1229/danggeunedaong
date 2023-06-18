package com.dgd.model.entity;

import com.dgd.model.dto.GoodDto;
import com.dgd.model.type.MainCategory;
import com.dgd.model.type.Status;
import com.dgd.model.type.SubCategory;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Good extends Base {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToMany(mappedBy = "good", orphanRemoval = true)
    private List<SharingApplication> sharingApplications;

    @ManyToOne
    private User user;

    @Enumerated(EnumType.STRING)
    private MainCategory mainCategory;


    @Enumerated(EnumType.STRING)
    private SubCategory subCategory;

    @NotBlank
    private String title;

    @NotBlank
    private String description;

    private Double latitude;

    private Double longitude;

    @Enumerated(EnumType.STRING)
    private Status status;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> goodImageList;

    @Column(nullable = false)
    private Long viewCnt;


    public void update(GoodDto.UpdateRequest form){
        this.mainCategory = form.getMainCategory();
        this.subCategory = form.getSubCategory();
        this.title = form.getTitle();
        this.description = form.getDescription();
        this.status = form.getStatus();
        this.goodImageList = form.getGoodImageList();
    }

    public GoodDto.Response toResponseDto(User user){
        return GoodDto.Response.builder()
                .offerNickName(user.getNickName())
                .mainCategory(this.mainCategory)
                .subCategory(this.subCategory)
                .title(this.title)
                .description(this.description)
                .location(user.getLocation())
                .status(this.status)
                .viewCnt(this.viewCnt)
                .goodImageList(this.goodImageList)
                .updatedAt(this.getUpdatedAt())
                .build();
    }

    public GoodDto.MyResponseList toResponsesDto(Integer sharingApplicationNum){
        return GoodDto.MyResponseList.builder()
                .title(this.title)
                .featuredImage(this.goodImageList)
                .updatedAt(this.getUpdatedAt())
                .location(this.getUser().getLocation())
                .sharingApplicationNum(sharingApplicationNum)
                .build();
    }

    public GoodDto.ResponseList toResponsesDto(){
        return  GoodDto.ResponseList.builder()
                .title(this.title)
                .location(this.getUser().getLocation())
                .featuredImages(this.goodImageList)
                .build();

    }

}
