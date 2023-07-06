package com.dgd.model.entity;

import com.dgd.model.dto.GoodDto;
import com.dgd.model.type.MainCategory;
import com.dgd.model.type.Status;
import com.dgd.model.type.SubCategory;
import com.dgd.service.EtcFeat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
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

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "good", orphanRemoval = true)
    private List<SharingApplication> sharingApplications;

    @JsonIgnore
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

    @OneToOne(orphanRemoval = true)
    private GoodViewCount goodViewCount;


    public void update(GoodDto.UpdateRequest form, List<String> goodImageList){
        this.mainCategory = form.getMainCategory();
        this.subCategory = form.getSubCategory();
        this.title = form.getTitle();
        this.description = form.getDescription();
        this.status = form.getStatus();
        this.goodImageList = goodImageList;
    }

    public Status updateStatus(Status status){
        if (Status.SHARING.equals(status)){
            this.status = Status.COMPLETE;
        } else {
            this.status = Status.SHARING;
        }
        return this.status;
    }

    public GoodDto.Response toResponseDto(User user){
        EtcFeat etcFeat = new EtcFeat();

        return GoodDto.Response.builder()
                .goodId(this.id)
                .offerNickName(user.getNickName())
                .mainCategory(this.mainCategory)
                .subCategory(this.subCategory)
                .title(this.title)
                .description(this.description)
                .location(etcFeat.getAddress(user.getLocation()))
                .status(this.status)
                .viewCnt(this.goodViewCount.getViewCount())
                .goodImageList(this.goodImageList)
                .updatedAt(this.getUpdatedAt())
                .build();
    }

    public GoodDto.MyResponseList toResponsesDto(Integer sharingApplicationNum){
        return GoodDto.MyResponseList.builder()
                .goodId(this.id)
                .title(this.title)
                .featuredImage(this.goodImageList)
                .updatedAt(this.getUpdatedAt())
                .location(this.getUser().getLocation())
                .status(this.status)
                .sharingApplicationNum(sharingApplicationNum)
                .build();
    }

    public GoodDto.ResponseList toResponsesDto(){
        return  GoodDto.ResponseList.builder()
                .goodId(this.id)
                .title(this.title)
                .latitude(this.latitude)
                .longitude(this.longitude)
                .status(this.status)
                .mainCategory(this.mainCategory)
                .subCategory(this.subCategory)
                .location(this.getUser().getLocation())
                .goodImages(this.goodImageList)
                .build();

    }

}
