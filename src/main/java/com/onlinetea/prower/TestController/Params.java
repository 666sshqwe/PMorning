package com.onlinetea.prower.TestController;

import lombok.*;

/**
 * @author ：yangguang
 * @date ：2021/8/19
 * @description：
 */
@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Params {

    private String field;
    private String operator;
    private Object value;
}
