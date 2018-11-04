package bol.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.bson.types.ObjectId;

public class AbstractMongoObject {

    @JsonIgnore
    private ObjectId _id;

    public AbstractMongoObject(ObjectId _id) {
        this._id = _id;
    }


}
