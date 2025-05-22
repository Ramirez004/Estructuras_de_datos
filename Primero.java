import java.util.Random;

class Producto {
    int codigo, cantidad;
    double precio;
    Producto sig;

    Producto(int c, int q, double p) {
        codigo = c;
        cantidad = q;
        precio = p;
        sig = null;
    }
}

public class ListaVentas {

    public static Producto poblarDatos() {
        Random r = new Random();
        Producto cabeza = null, actual = null;

        for (int i = 0; i < 10; i++) {
            int cod = 1020 + r.nextInt(5); // códigos entre 1020 y 1024
            int cant = 1 + r.nextInt(10);
            double prec = 50 + r.nextDouble() * 100;
            Producto nuevo = new Producto(cod, cant, prec);

            if (cabeza == null) cabeza = nuevo;
            else actual.sig = nuevo;
            actual = nuevo;
        }
        return cabeza;
    }

    public static void mostrarDatos(Producto lista) {
        for (Producto p = lista; p != null; p = p.sig)
            System.out.printf("Código: %d, Cantidad: %d, Precio: %.2f\n", p.codigo, p.cantidad, p.precio);
    }

    public static Producto sumarVentas(Producto entrada) {
        Producto salida = null;

        for (Producto p = entrada; p != null; p = p.sig) {
            Producto q = buscar(salida, p.codigo);
            if (q == null) {
                q = new Producto(p.codigo, p.cantidad, p.precio);
                salida = agregar(salida, q);
            } else {
                q.precio = (q.precio * q.cantidad + p.precio * p.cantidad) / (q.cantidad + p.cantidad);
                q.cantidad += p.cantidad;
            }
        }
        return salida;
    }

    public static Producto buscar(Producto lista, int cod) {
        for (Producto p = lista; p != null; p = p.sig)
            if (p.codigo == cod) return p;
        return null;
    }

    public static Producto agregar(Producto lista, Producto nuevo) {
        if (lista == null) return nuevo;
        Producto p = lista;
        while (p.sig != null) p = p.sig;
        p.sig = nuevo;
        return lista;
    }

    public static void main(String[] args) {
        Producto listaEntrada = poblarDatos();
        System.out.println("---- Lista Entrada ----");
        mostrarDatos(listaEntrada);

        Producto listaSalida = sumarVentas(listaEntrada);
        System.out.println("---- Lista Totalizada ----");
        mostrarDatos(listaSalida);
    }
}
