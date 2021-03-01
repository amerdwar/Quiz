
package com.quiz.quiz.dto;

        import com.quiz.quiz.utils.ResponeStatus;
        import lombok.AllArgsConstructor;
        import lombok.Data;

        import java.util.List;
@Data
@AllArgsConstructor
public class ObjectResponse <T>{
    ResponeStatus status;
    String message;
    T data;
}
