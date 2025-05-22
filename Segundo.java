import java.util.Random;

public class NominaEmpresa {

    public static void poblarDatos(int[] ids, String[] nombres, double[][] datos) {
        String[] baseNombres = {"Juanita", "Pachita", "Albita", "Pedrito", "Damaris"};
        Random r = new Random();

        for (int i = 0; i < ids.length; i++) {
            ids[i] = 1000 + r.nextInt(50);
            nombres[i] = baseNombres[i];
            datos[i][0] = 150000 + r.nextInt(200000); // Sueldo básico
            datos[i][1] = 50000 + r.nextInt(60000);   // Deducciones
            datos[i][2] = datos[i][0] - datos[i][1];  // Neto a pagar
        }
    }

    public static void ordenarPorIdentificacion(int[] ids, String[] nombres, double[][] datos) {
        for (int i = 0; i < ids.length - 1; i++) {
            int min = i;
            for (int j = i + 1; j < ids.length; j++)
                if (ids[j] < ids[min]) min = j;
            intercambiar(ids, nombres, datos, i, min);
        }
    }

    public static int buscarEmpleado(int[] ids, int clave) {
        int ini = 0, fin = ids.length - 1;
        while (ini <= fin) {
            int mid = (ini + fin) / 2;
            if (ids[mid] == clave) return mid;
            else if (ids[mid] < clave) ini = mid + 1;
            else fin = mid - 1;
        }
        return -1;
    }

    public static void ordenarPorNombre(int[] ids, String[] nombres, double[][] datos, int izq, int der) {
        if (izq >= der) return;
        String pivote = nombres[izq];
        int i = izq + 1, j = der;

        while (i <= j) {
            while (i <= der && nombres[i].compareTo(pivote) < 0) i++;
            while (j > izq && nombres[j].compareTo(pivote) > 0) j--;
            if (i < j) intercambiar(ids, nombres, datos, i, j);
        }
        intercambiar(ids, nombres, datos, izq, j);
        ordenarPorNombre(ids, nombres, datos, izq, j - 1);
        ordenarPorNombre(ids, nombres, datos, j + 1, der);
    }

    public static void mostrarDatos(int[] ids, String[] nombres, double[][] datos) {
        System.out.println("Nombre\t\tID\tSueldo\tDeducciones\tNeto");
        for (int i = 0; i < ids.length; i++) {
            System.out.printf("%-10s\t%d\t%.0f\t%.0f\t\t%.0f\n",
                nombres[i], ids[i], datos[i][0], datos[i][1], datos[i][2]);
        }
    }

    private static void intercambiar(int[] ids, String[] nombres, double[][] datos, int a, int b) {
        int tempId = ids[a]; ids[a] = ids[b]; ids[b] = tempId;

        String tempNom = nombres[a]; nombres[a] = nombres[b]; nombres[b] = tempNom;

        for (int k = 0; k < 3; k++) {
            double temp = datos[a][k];
            datos[a][k] = datos[b][k];
            datos[b][k] = temp;
        }
    }

    public static void main(String[] args) {
        int[] ids = new int[5];
        String[] nombres = new String[5];
        double[][] datos = new double[5][3];

        poblarDatos(ids, nombres, datos);
        ordenarPorIdentificacion(ids, nombres, datos);

        int pos = buscarEmpleado(ids, ids[2]); // ejemplo de búsqueda
        if (pos >= 0) {
            System.out.println("\nEmpleado encontrado:");
            System.out.printf("%s, Sueldo: %.0f, Deducciones: %.0f, Neto: %.0f\n",
                nombres[pos], datos[pos][0], datos[pos][1], datos[pos][2]);
        } else {
            System.out.println("Empleado no encontrado");
        }

        ordenarPorNombre(ids, nombres, datos, 0, ids.length - 1);

        System.out.println("\n--- Nómina ordenada por nombre ---");
        mostrarDatos(ids, nombres, datos);
    }
}

