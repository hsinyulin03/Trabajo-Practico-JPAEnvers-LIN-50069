package org.example;
import lombok.*;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString (exclude = "cliente")
@Audited
@Entity
@Table(name="domicilio")
public class Domicilio implements Serializable {
    private static final long serialVersionID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private int id;
    private String nombreCalle;
    private int numero;
    @OneToOne(mappedBy="domicilio")
    private Cliente cliente;

}
