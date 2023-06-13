package com.dgd.model.entity;

import com.dgd.model.dto.GoodDto;
import com.dgd.model.type.MainCategory;
import com.dgd.model.type.Status;
import com.dgd.model.type.SubCategory;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
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

    @ManyToOne
    private User user;

    @Enumerated(EnumType.STRING)
    private MainCategory mainCategory;


    @Enumerated(EnumType.STRING)
    private SubCategory subCategory;

    @NotEmpty
    private String title;

    @NotEmpty
    private String description;

    @NotNull
    private Double latitude;

    @NotNull
    private Double longitude;

    @Enumerated(EnumType.STRING)
    private Status status;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> goodImageList;

    private Long view_cnt;

    public GoodDto.Response toResponseDto(User user){
        return GoodDto.Response.builder()
                .offerNickName(user.getNickName())
                .mainCategory(this.mainCategory)
                .subCategory(this.subCategory)
                .title(this.title)
                .description(this.description)
                .location(user.getLocation())
                .status(this.status)
                .view_cnt(this.view_cnt)
                .goodImageList(this.goodImageList)
                .build();
    }

}
