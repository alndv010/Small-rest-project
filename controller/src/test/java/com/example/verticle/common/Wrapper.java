package com.example.verticle.common;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by remote on 9/29/17.
 */
@Data
@AllArgsConstructor
public class Wrapper<T> {
    private T response;

    private int code;
}
