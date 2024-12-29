package edu.sharif.webclass.hw2.formmaker.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity(name = "forms")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Form {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Boolean status;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private HttpMethod method;

    @Column(nullable = false)
    private String finalAddress;

    @OneToMany(mappedBy = "form")
    @JsonManagedReference
    private List<Field> fields;
}
