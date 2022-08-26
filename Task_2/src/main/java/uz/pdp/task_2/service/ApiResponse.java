package uz.pdp.task_2.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class ApiResponse {

    private String message;
    private boolean success;
    private Object object;
    private Object object1;
    private Object object2;
    private Object object3;
    private Object object4;

    public ApiResponse(Object object, Object object1, Object object2, Object object3,Object object4) {
        this.object = object;
        this.object1 = object1;
        this.object2 = object2;
        this.object3 = object3;
        this.object4 = object4;
    }

    public ApiResponse(String message, boolean success, Object object, Object object1) {
        this.message = message;
        this.success = success;
        this.object = object;
        this.object1 = object1;
    }

    private List<Object> all;


    public ApiResponse(String message, boolean success) {
        this.message = message;
        this.success = success;
    }

    public ApiResponse(String message, boolean success, Object object) {
        this.message = message;
        this.success = success;
        this.object=object;
    }

    public ApiResponse(String message, boolean success,List<Object> all) {
        this.message=message;
        this.success=success;
        this.all= all;
    }


}
