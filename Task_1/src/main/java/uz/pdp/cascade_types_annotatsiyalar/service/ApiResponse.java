package uz.pdp.cascade_types_annotatsiyalar.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pdp.cascade_types_annotatsiyalar.entity.Detailing;
import uz.pdp.cascade_types_annotatsiyalar.entity.TourniquetCard;

import java.util.List;
import java.util.Map;


@Data
@NoArgsConstructor

public class ApiResponse {

    private String message;

    private boolean success;

    private List<Object> all;

    private Object object;


    public ApiResponse(String message, boolean success, List<Object> all) {
        this.message=message;
        this.success=success;
        this.all= all;
    }

    public ApiResponse(String message, boolean success) {
        this.message = message;
        this.success = success;
    }

    public ApiResponse(String message, boolean success, Object object) {
        this.message = message;
        this.success = success;
        this.object=object;
    }

    public ApiResponse(Object object, boolean success) {
        this.success = success;
        this.object=object;
    }

    public ApiResponse(List<TourniquetCard> all, String s, boolean success) {
        this.message=s;
        this.success=success;
        this.object=all;
    }

    public ApiResponse(String s, boolean success, Map<String,Integer> all) {
        this.message=s;
        this.success=success;
        this.object=all;
    }


}
