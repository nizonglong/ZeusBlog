package com.nzl.model.pojo;

import lombok.*;

import javax.persistence.Table;
import java.io.Serializable;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_role")
public class UserRole  implements Serializable {
    private String uid;
    private Integer rid;
}
