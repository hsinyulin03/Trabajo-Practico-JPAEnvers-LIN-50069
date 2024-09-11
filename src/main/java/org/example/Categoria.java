package org.example;
import lombok.*;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString (exclude ="articulo")
@Audited
@Entity
@Table(name="categoria")
public class Categoria implements Serializable {
    private static final long serialVersionID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    private String denominacion;
    @ManyToMany(mappedBy = "categorias")
    @Builder.Default
    private List<Articulo> articulos=new ArrayList<Articulo>();
}
