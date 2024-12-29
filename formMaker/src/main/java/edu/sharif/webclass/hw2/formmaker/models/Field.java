package edu.sharif.webclass.hw2.formmaker.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "fields")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Field {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String fieldName;

    @Column(nullable = false)
    private String label;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private FieldType type;

    @Column(nullable = false)
    private String defaultValue;

    @ManyToOne()
    @JoinColumn(name = "form_id")
    @JsonBackReference
    private Form form;
}
