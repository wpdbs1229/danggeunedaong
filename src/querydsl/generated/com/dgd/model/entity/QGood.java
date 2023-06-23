package com.dgd.model.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QGood is a Querydsl query type for Good
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QGood extends EntityPathBase<Good> {

    private static final long serialVersionUID = -1799620695L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QGood good = new QGood("good");

    public final QBase _super = new QBase(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final StringPath description = createString("description");

    public final ListPath<String, StringPath> goodImageList = this.<String, StringPath>createList("goodImageList", String.class, StringPath.class, PathInits.DIRECT2);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Double> latitude = createNumber("latitude", Double.class);

    public final NumberPath<Double> longitude = createNumber("longitude", Double.class);

    public final EnumPath<com.dgd.model.type.MainCategory> mainCategory = createEnum("mainCategory", com.dgd.model.type.MainCategory.class);

    public final ListPath<SharingApplication, QSharingApplication> sharingApplications = this.<SharingApplication, QSharingApplication>createList("sharingApplications", SharingApplication.class, QSharingApplication.class, PathInits.DIRECT2);

    public final EnumPath<com.dgd.model.type.Status> status = createEnum("status", com.dgd.model.type.Status.class);

    public final EnumPath<com.dgd.model.type.SubCategory> subCategory = createEnum("subCategory", com.dgd.model.type.SubCategory.class);

    public final StringPath title = createString("title");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public final QUser user;

    public final NumberPath<Long> viewCnt = createNumber("viewCnt", Long.class);

    public QGood(String variable) {
        this(Good.class, forVariable(variable), INITS);
    }

    public QGood(Path<? extends Good> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QGood(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QGood(PathMetadata metadata, PathInits inits) {
        this(Good.class, metadata, inits);
    }

    public QGood(Class<? extends Good> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user")) : null;
    }

}

