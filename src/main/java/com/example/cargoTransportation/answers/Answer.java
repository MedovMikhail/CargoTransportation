package com.example.cargoTransportation.answers;

import com.example.cargoTransportation.errors.ErrorSituation;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Answer <T> {
    private T body;
    private ErrorSituation error;

    public static <T> Answer<T> answer(ErrorSituation error){
        return new Answer<>(null, error);
    }

    public static <T> Answer<T> answer(T body){
        return new Answer<>(body, null);
    }
}
