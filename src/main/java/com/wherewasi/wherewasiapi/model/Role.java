package com.wherewasi.wherewasiapi.model;

import com.wherewasi.wherewasiapi.enumeration.UserRole;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Getter
@Setter
@Document(collection = "roles")
public class Role {
    @MongoId
    private ObjectId id;
    private UserRole name;

    public Role() {
    }

    public Role(UserRole name) {
        this.name = name;
    }

}
