package com.svf.fpl.edu.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

//@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "admin_roles")
public class AdminUserRole implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "role")
    private String role;
}
