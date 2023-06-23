package com.dgd.model.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUser is a Querydsl query type for User
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUser extends EntityPathBase<User> {

    private static final long serialVersionUID = -1799200073L;

    public static final QUser user = new QUser("user");

    public final ListPath<Good, QGood> goods = this.<Good, QGood>createList("goods", Good.class, QGood.class, PathInits.DIRECT2);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Double> latitude = createNumber("latitude", Double.class);

    public final StringPath location = createString("location");

    public final NumberPath<Double> longitude = createNumber("longitude", Double.class);

    public final StringPath nickName = createString("nickName");

    public final StringPath password = createString("password");

    public final ListPath<Pet, QPet> petList = this.<Pet, QPet>createList("petList", Pet.class, QPet.class, PathInits.DIRECT2);

    public final StringPath profileUrl = createString("profileUrl");

    public final EnumPath<com.dgd.model.type.Role> role = createEnum("role", com.dgd.model.type.Role.class);

    public final ListPath<SharingApplication, QSharingApplication> sharingApplications = this.<SharingApplication, QSharingApplication>createList("sharingApplications", SharingApplication.class, QSharingApplication.class, PathInits.DIRECT2);

    public final StringPath socialId = createString("socialId");

    public final EnumPath<com.dgd.model.type.SocialType> socialType = createEnum("socialType", com.dgd.model.type.SocialType.class);

    public final StringPath token = createString("token");

    public final StringPath userId = createString("userId");

    public QUser(String variable) {
        super(User.class, forVariable(variable));
    }

    public QUser(Path<? extends User> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUser(PathMetadata metadata) {
        super(User.class, metadata);
    }

}

