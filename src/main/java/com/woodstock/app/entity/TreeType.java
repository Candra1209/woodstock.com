package com.woodstock.app.entity;

import com.woodstock.app.models.response.tree_type.TreeTypeResponse;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SoftDelete;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@SQLDelete(sql = "UPDATE tree_type SET deleted = true WHERE id = ?")
public class TreeType extends BaseEntity {

    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;

    @NotBlank
    private String name;

    @Column(name = "type_code")
    private String typeCode;

    @Column(name = "deleted")
    private boolean isDeleted = false;

    public TreeTypeResponse toResponse(){
        return TreeTypeResponse.builder()
                .id(this.id)
                .name(this.name)
                .typeCode(this.typeCode)
                .createAt(this.getCreateAt())
                .updateAt(this.getUpdateAt())
                .build();
    }


}
