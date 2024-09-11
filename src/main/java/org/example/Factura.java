package org.example;
import lombok.*;
import org.hibernate.engine.internal.Cascade;
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
@ToString
@Audited
@Entity
@Table(name="factura")
public class Factura implements Serializable {
    private static final long serialVersionID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    private String fecha;
    private int numero;
    private int total;
    @ManyToOne(cascade= CascadeType.PERSIST)
    @JoinColumn(name="fk_cliente")
    private Cliente cliente;

    @OneToMany(mappedBy = "factura", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<DetalleFactura> detalle= new ArrayList<DetalleFactura>();
}
