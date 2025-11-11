package com.example.EdufyMusic.utilities;

import com.example.EdufyMusic.exceptions.NoContentException;

import java.util.List;

public class MicroMethodes {

    private MicroMethodes() {}

    public static <T> void validateListNotEmpty(List<T> list, String resourceName) {
        if (list == null || list.isEmpty()) {
            throw new NoContentException(resourceName);
        }
    }
}
