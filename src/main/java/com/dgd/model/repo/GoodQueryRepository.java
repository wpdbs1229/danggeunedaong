package com.dgd.model.repo;

import com.dgd.model.entity.Good;
import com.dgd.model.type.MainCategory;
import com.dgd.model.type.Status;
import com.dgd.model.type.SubCategory;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import static com.dgd.model.entity.QGood.good;

@RequiredArgsConstructor
@Repository
public class GoodQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;
    public Slice<Good> findWithSearchConditions(final String keyword,
                                                final Double minLatitude,
                                                final Double minLongitude,
                                                final Double maxLatitude,
                                                final Double maxLongitude,
                                                final MainCategory mainCategory,
                                                final SubCategory subCategory,
                                                final Status status,
                                                final Pageable pageable){
        List<Good> goodList =  jpaQueryFactory.selectFrom(good)
                .where(
                        toContainsKeyword(keyword),
                        eqMainCategory(mainCategory),
                        eqSubCategory(subCategory),
                        eqStatus(status),
                        gtMinLatitude(minLatitude),
                        gtMinLongitude(minLongitude),
                        ltMaxLatitude(maxLatitude),
                        ltMaxLongitude(maxLongitude)
                )
                .fetch();

        Integer total = goodList.size();
        Integer limit = pageable.getPageSize() * (pageable.getPageNumber()+1);

        List<Good> content = new ArrayList<>();

        for (int i = pageable.getPageNumber(); i < limit && i <total; i++){
            content.add(goodList.get(i));
        }
        return new PageImpl<>(content, pageable, total);
    }


    private BooleanExpression toContainsKeyword(String keyword){
        if (keyword.equals(null)||StringUtils.hasText(keyword) ){
            return null;
        }
        return good.title.contains(keyword);
    }

    private BooleanExpression eqMainCategory(final MainCategory mainCategory){
        if (mainCategory == null) {
            return null;
        }
        return good.mainCategory.eq(mainCategory);
    }

    private BooleanExpression eqSubCategory(final SubCategory subCategory){
        if (subCategory == null){
            return null;
        }
        return good.subCategory.eq(subCategory);
    }

    private BooleanExpression eqStatus(final Status status){
        if (status == null){
            return null;
        }
        return good.status.eq(status);
    }

    private BooleanExpression gtMinLatitude(final Double minLatitude){
        if (minLatitude == null || minLatitude.isNaN() || minLatitude.isInfinite()){
            return null;
        }
        return good.latitude.gt(minLatitude);
    }

    private BooleanExpression gtMinLongitude(final Double minLongitude){
        if (minLongitude == null || minLongitude.isNaN() || minLongitude.isInfinite()){
            return null;
        }
        return good.longitude.gt(minLongitude);
    }

    private BooleanExpression ltMaxLatitude(final Double maxLatitude){
        if (maxLatitude == null || maxLatitude.isNaN() || maxLatitude.isInfinite()){
            return null;
        }
        return good.latitude.lt(maxLatitude);
    }

    private BooleanExpression ltMaxLongitude(final Double maxLongitude){
        if (maxLongitude == null || maxLongitude.isNaN() || maxLongitude.isInfinite()){
            return null;
        }
        return good.longitude.lt(maxLongitude);
    }
}
