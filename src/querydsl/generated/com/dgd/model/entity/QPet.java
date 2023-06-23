package com.dgd.model.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QPet is a Querydsl query type for Pet
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPet extends EntityPathBase<Pet> {

    private static final long serialVersionUID = -1443517261L;

    public static final QPet pet = new QPet("pet");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Integer> petAge = createNumber("petAge", Integer.class);

    public final EnumPath<com.dgd.model.type.PetGender> petGender = createEnum("petGender", com.dgd.model.type.PetGender.class);

    public final StringPath petName = createString("petName");

    public final EnumPath<com.dgd.model.type.PetSize> petSize = createEnum("petSize", com.dgd.model.type.PetSize.class);

    public final EnumPath<com.dgd.model.type.MainCategory> petType = createEnum("petType", com.dgd.model.type.MainCategory.class);

    public final NumberPath<Long> userId = createNumber("userId", Long.class);

    public QPet(String variable) {
        super(Pet.class, forVariable(variable));
    }

    public QPet(Path<? extends Pet> path) {
        super(path.getType(), path.getMetadata());
    }

    public QPet(PathMetadata metadata) {
        super(Pet.class, metadata);
    }

}

