package main.java.karchevsky.apache.ilb.model.meta;


import lombok.*;

import javax.persistence.*;
import java.io.Serializable;


@Getter
@Setter
@Entity
@Table(name = "parameter_meta")
public class ParameterMeta implements Serializable {
    /**
     * field for index
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "parameter_id")
    private Long parameterId;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

}