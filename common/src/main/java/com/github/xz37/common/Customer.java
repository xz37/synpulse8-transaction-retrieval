package com.github.xz37.common;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
    private String id;
    private List<Account> accounts;

}
