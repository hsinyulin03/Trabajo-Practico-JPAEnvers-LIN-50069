package org.example;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("example-unit");

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        System.out.println("Estamos en Marcha");

        try {
           entityManager.getTransaction().begin();

            //Creamos el cliente
            Cliente cliente1 = Cliente.builder()
                    .nombre("Romina")
                    .apellido("Suarez")
                    .dni(3898274)
                    .build();
            //Creamos el domicilio
            Domicilio domicilio1= Domicilio.builder()
                    .nombreCalle("Jorge A Calle")
                    .numero(2467)
                    .build();

            //Asignamos el domicilio al cliente (bidireccional)
            cliente1.setDomicilio(domicilio1);
            domicilio1.setCliente(cliente1);

            //Creamos las categorias
            Categoria perecederos= Categoria.builder()
                    .denominacion("Perecederos")
                    .build();
            Categoria lacteos= Categoria.builder()
                    .denominacion("Lacteos")
                    .build();
            Categoria limpieza= Categoria.builder()
                    .denominacion("Limpieza")
                    .build();

            //Creamos los articulos
            Articulo articulo1= Articulo.builder()
                    .denominacion("Leche Entera La serenisima")
                    .cantidad(100)
                    .precio(700)
                    .build();

            Articulo articulo2= Articulo.builder()
                    .denominacion("Lavandina Ayudin")
                    .cantidad(200)
                    .precio(1000)
                    .build();
            //Creamos la factura 1
            Factura factura1= Factura.builder()
                    .numero(100)
                    .fecha("03/03/2024")
                    .cliente(cliente1)
                    .build();

            // Ahora al art1 le asignamos las categorias
            articulo1.getCategorias().add(perecederos);
            articulo1.getCategorias().add(lacteos);

            // Ahora a las categorias le asignamos el art1
            lacteos.getArticulos().add(articulo1);
            perecederos.getArticulos().add(articulo1);

            // Ahora al art2 le asignamos la categoria
            articulo2.getCategorias().add(limpieza);
            // Ahora a las categorias le asignamos el art2
            limpieza.getArticulos().add(articulo2);

            //Creamos detalles facturas
            DetalleFactura detalle1= DetalleFactura.builder()
                    .cantidad(3)
                    .articulo(articulo1)
                    .build();
            detalle1.setSubtotal(detalle1.getCantidad()*detalle1.getArticulo().getPrecio());
            articulo1.getDetalle().add(detalle1);
            factura1.getDetalle().add(detalle1);
            detalle1.setFactura(factura1);

            //Creamos otro detalle de factura
            DetalleFactura detalle2= DetalleFactura.builder()
                    .cantidad(1)
                    .articulo(articulo2)
                    .build();
            detalle2.setSubtotal(detalle2.getCantidad()*detalle2.getArticulo().getPrecio());
            articulo2.getDetalle().add(detalle2);
            factura1.getDetalle().add(detalle2);
            detalle2.setFactura(factura1);

            factura1.setTotal(detalle1.getSubtotal()+ detalle2.getSubtotal());

            entityManager.persist(factura1);

            //Creamos el cliente
            Cliente cliente2 = Cliente.builder()
                    .nombre("Hsin Yu")
                    .apellido("Lin")
                    .dni(4802772)
                    .build();

            //Creamos el domicilio
            Domicilio domicilio2= Domicilio.builder()
                    .nombreCalle("San Martin")
                    .numero(3893)
                    .build();

            //Asignamos el domicilio al cliente (bidireccional)
            cliente2.setDomicilio(domicilio2);
            domicilio2.setCliente(cliente2);

            //Creamos los articulos
            Articulo articulo3= Articulo.builder()
                    .denominacion("Queso cremoso")
                    .cantidad(300)
                    .precio(2000)
                    .build();
            //Cremaos otra factura
            Factura factura2= Factura.builder()
                    .numero(101)
                    .fecha("05/03/2024")
                    .cliente(cliente2)
                    .build();
            // Ahora al art3 le asignamos las categorias
            articulo3.getCategorias().add(perecederos);
            articulo3.getCategorias().add(lacteos);
            // Ahora a las categorias le asignamos el art3
            lacteos.getArticulos().add(articulo3);
            perecederos.getArticulos().add(articulo3);


            //Creamos un detalle factura
            DetalleFactura detalle3= DetalleFactura.builder()
                    .cantidad(2)
                    .articulo(articulo3)
                    .build();
            detalle3.setSubtotal(detalle3.getCantidad()*detalle3.getArticulo().getPrecio());

            articulo3.getDetalle().add(detalle3);
            factura2.getDetalle().add(detalle3);
            detalle3.setFactura(factura2);

            //Creamos otro detalle factura
            DetalleFactura detalle4= DetalleFactura.builder()
                    .cantidad(1)
                    .articulo(articulo1)
                    .build();
            detalle4.setSubtotal(detalle4.getCantidad()*detalle4.getArticulo().getPrecio());
            articulo1.getDetalle().add(detalle4);
            factura2.getDetalle().add(detalle4);
            detalle4.setFactura(factura2);

            factura2.setTotal(detalle3.getSubtotal()+ detalle4.getSubtotal());
            entityManager.persist(factura2);

            entityManager.flush();
            entityManager.getTransaction().commit();


            //Actualizar la factura
            entityManager.getTransaction().begin();
            Factura factura3 = entityManager.find(Factura.class, 1L);
            factura3.setNumero(35);
            entityManager.merge(factura3);
            entityManager.getTransaction().commit();
            System.out.println("La factura ha sido actualizada exitosamente "+factura3);


           /* // Eliminar la factura
            entityManager.getTransaction().begin();
            Factura factura4 = entityManager.find(Factura.class, 1L);
            entityManager.remove(factura4);
            entityManager.getTransaction().commit();
            System.out.println("La factura ha sido eliminada exitosamente "+factura4);*/



        }catch (Exception e){
            entityManager.getTransaction().rollback();
            System.out.println(e.getMessage());
            System.out.println("No se pudo grabar la clase factura");
        }

        // Cerrar el EntityManager y el EntityManagerFactory
        entityManager.close();
        entityManagerFactory.close();


    }

}