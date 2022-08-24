package com.example.springrediscache.Model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;
@Data
@EqualsAndHashCode(exclude = "invoice")
@ToString(exclude = "invoice")
public class User implements Serializable {
    private String name;
    private int age;
    private String address;
    private String job;
}
