package com.dgd.model.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSharingApplication is a Querydsl query type for SharingApplication
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSharingApplication extends EntityPathBase<SharingApplication> {

    private static final long serialVersionUID = -975452992L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSharingApplication sharingApplication = new QSharingApplication("sharingApplication");

    public final StringPath content = createString("content");

    public final NumberPath<Double> distance = createNumber("distance", Double.class);

    public final QGood good;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final DateTimePath<java.time.LocalDateTime> requestedAt = createDateTime("requestedAt", java.time.LocalDateTime.class);

    public final QUser user;

    public QSharingApplication(String variable) {
        this(SharingApplication.class, forVariable(variable), INITS);
    }

    public QSharingApplication(Path<? extends SharingApplication> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSharingApplication(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSharingApplication(PathMetadata metadata, PathInits inits) {
        this(SharingApplication.class, metadata, inits);
    }

    public QSharingApplication(Class<? extends SharingApplication> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.good = inits.isInitialized("good") ? new QGood(forProperty("good"), inits.get("good")) : null;
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user")) : null;
    }

}

