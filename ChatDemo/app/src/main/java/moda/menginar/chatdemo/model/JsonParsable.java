package moda.menginar.chatdemo.model;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Created by Menginar on 1.4.2017.
 */

public abstract class JsonParsable {

    public String toJson() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(this);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
