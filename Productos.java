import java.util.*; //importar todo lo necesario

public class Productos {
    //atributo(s)
    private int id;
    private String categoria;
    private String descripcion;
    private int cantidad;
    private String marca;
    private double precio;

    //activar scanner
    static Scanner sc = new Scanner(System.in);

    //constructor por parámetros
    public Productos (int id, String categoria, String descripcion, int cantidad, String marca, double precio){
        this.id = id;
        this.categoria = categoria;
        this.descripcion = descripcion;
        this.cantidad = cantidad;
        this.marca = marca;
        this.precio = precio;
    }

    //getters y setters

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategoria() {
        return this.categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getCantidad() {
        return this.cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getMarca() {
        return this.marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public double getPrecio() {
        return this.precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    //método to string
    @Override
    public String toString() {
        return "Productos [id=" + id + ", categoria=" + categoria + ", descripcion=" + descripcion + ", cantidad="
                + cantidad + ", marca=" + marca + ", precio=" + precio + "]";
    }


    /**
     * método para eliminar productos por marca
     */
    public static void eliminarPorMarca(){
        //pedir la marca del producto a eliminar
        System.out.print("\nPor favor, introduce la marca del producto a eliminar: ");
        String marcaProductoEliminar = sc.next();

        //llamar al método de la clase DAO
        ProductosDAO.eliminarPorMarca(marcaProductoEliminar); //pasarle la marca como parámetro
    }

    /**
     * método para añadir un producto
     */
    public static void anadirProducto(){
        // pedir el identificador del producto
        System.out.print("\nPor favor, introduce el ID del producto: ");
        int idProducto = sc.nextInt();

        // pedir la categoria del producto
        System.out.print("\nPor favor, escribe la categoría del producto: ");
        String categoriaProducto = sc.next();

        // pedir la descripción (nombre) del producto
        System.out.print("\nPor favor, introduce la descripción del producto: ");
        sc.nextLine();
        String descripcionProducto = sc.nextLine();

        // pedir la cantidad de productos a añadir
        System.out.print("\nPor favor, introduce la cantidad de productos que deseas añadir: ");
        int cantidadProducto = sc.nextInt();

        // pedir la marca del producto
        System.out.print("\nPor favor, escribe la marca del producto: ");
        sc.nextLine();
        String marcaProducto = sc.nextLine();

        // pedir el precio del producto
        System.out.print("\nPor favor, introduce el precio del producto: ");
        double precioProducto = sc.nextDouble();

        // añadir los datos a la lista de productos
        Productos nuevoProducto = new Productos(idProducto, categoriaProducto, descripcionProducto, cantidadProducto, marcaProducto, precioProducto);

        //llamar al método de la clase DAO
        ProductosDAO.anadirProducto(nuevoProducto); //pasarle el objeto como parámetro
    }
    
    /**
     * método para mostrar los productos
     */
    public static void mostrarProductos(){
        //lista 
        ArrayList<Productos> listaProductos = new ArrayList<>();

        //llamar al método de la clase DAO
        ProductosDAO.mostrarProductos(listaProductos); //pasarle el arraylist como parámetro

        //mostrar los datos
        for (Productos productos : listaProductos) {
            System.out.println("\n" + productos);
        }
    }

    /**
     * método para añadir stock a un producto
     */
    public static void anadirStock(){
        //pedir el id del producto al que se desea añadir stock
        System.out.print("\nPor favor, introduce el ID del producto al que hay que sumar stock: ");
        int idProducto = sc.nextInt();

        //pedir la cantidad de unidades a sumar
        System.out.print("\nPor favor, introduce el número de unidades a sumar: ");
        int cantidadSumar = sc.nextInt();

        //llamar al método de la clase DAO
        ProductosDAO.anadirStock(idProducto, cantidadSumar); //pasar los datos como parámetros
    }

}
