package com.woodstock.app.entity;

import com.woodstock.app.models.response.tree.TreeMinResponse;
import com.woodstock.app.models.response.tree.TreeResponse;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@Table(name = "tree")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Tree {

    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;

    @Column(nullable = false)
    private Double length;

    @Column(name = "bottom_diameter")
    private Double bottomDiameter;

    @Column(name = "top_diameter")
    private Double topDiameter;

    @Column(name = "avg_diameter")
    private Double avgDiameter;

    private Double volume;

    @ManyToOne
    @JoinColumn(name = "tree_type_id")
    private TreeType treeType;

    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;

    @ManyToOne
    @JoinColumn(name = "scaller_account_id")
    private AccountInfo scaller;

    @ManyToOne
    @JoinColumn(name = "faller_account_id")
    private AccountInfo faller;

    public TreeMinResponse toMinResponse(){
       return TreeMinResponse.builder()
               .id(id)
               .scllerId(scaller.getId())
               .scaller(scaller.getFullname())
               .fallerId(faller.getId())
               .faller(faller.getFullname())
               .type(treeType.getName())
               .location(location.getName())
               .length(length)
               .bottomDiameter(bottomDiameter)
               .topDiameter(topDiameter)
               .avgDiameter(avgDiameter)
               .volume(volume)
               .build();
    }

    public TreeResponse toTreeResponse() {
        return TreeResponse.builder()
                .id(this.id)
                .scaller(scaller.toResponse())
                .faller(faller.toResponse())
                .location(location.toResponse())
                .type(treeType.toResponse())
                .length(this.length)
                .bottomDiameter(this.bottomDiameter)
                .topDiameter(topDiameter)
                .avgDiameter(avgDiameter)
                .volume(volume)
                .build();
    }

}
