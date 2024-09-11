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
@ToString
@Builder
@Audited //indicamos que esta tabla se va a auditar
@Entity
@Table(name="articulo")
public class Articulo implements Serializable {
    private static final long serialVersionID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    private int cantidad;
    private int precio;
    private String denominacion;

    @OneToMany(mappedBy = "articulo", cascade = CascadeType.PERSIST)
    @Builder.Default
    private List<DetalleFactura> detalle= new ArrayList<DetalleFactura>();

    @ManyToMany(cascade= {CascadeType.PERSIST,CascadeType.MERGE})
    @JoinTable( name = "articulo_categoria",
    joinColumns = @JoinColumn(name = "articulo_id"),
    inverseJoinColumns = @JoinColumn (name="categoria_id"))
    @Builder.Default
    private List<Categoria> categorias= new ArrayList<Categoria>();
}
