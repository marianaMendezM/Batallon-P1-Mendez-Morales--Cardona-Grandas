package co.edu.uniquindio;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import java.util.logging.Logger;
import java.time.LocalDate;

class BatallonTest {
    private Batallon batallon;
    private static final Logger LOG = Logger.getLogger(BatallonTest.class.getName());


    @BeforeEach
    void setUp() {
        batallon = new Batallon("01", "Cacique", "03", "Calarcá", 3);
    }

    @Test
    @DisplayName("Prueba assertTrue/Null Vehiculo batallon")
    void testRegistrarVehiculoCorrecto() {
        TransporteTropas transporte = new TransporteTropas("T-01", "Camión de tropas", 2020, 12000, EstadoOperativo.DISPONIBLE, 25);

        LOG.info("Iniciando prueba assertTrue/Null Vehiculo correcto en batallon: ");

        boolean registrado = batallon.registrarVehiculo(transporte);

        assertTrue(registrado);
        assertNotNull(batallon.buscarVehiculo("T-01"));

    LOG.info("Finalizando prueba assertTrue/Null Vehiculo correcto en batallon");
    }

    /*No permitir registrar vehículos con ID repetido*/
    @Test
    @DisplayName("Prueba assertFalse vRepetido batallon")
    void testRegistrarVehiculoDuplicado() {
        TransporteTropas v1 = new TransporteTropas("T-01", "Camión de tropas", 2020, 12000, EstadoOperativo.DISPONIBLE, 25);
        Blindado v2 = new Blindado("T-01", "Vehículo blindado", 2021, 8000, EstadoOperativo.DISPONIBLE, 4);

        LOG.info("Iniciando prueba assertFalse Vehiculo repetido: ");

        batallon.registrarVehiculo(v1);
        boolean registradoDuplicado = batallon.registrarVehiculo(v2);

        assertFalse(registradoDuplicado);
        LOG.info("Finalizando prueba assertFalse Vehiculo repetido");
    }

    /*Buscar vehiculos que existan e inexistentes*/
    @Test
    @DisplayName("Prueba assertNull - assertEquals - assertNotNull vExistente batallon")
    void testBuscarVehiculoExistenteEInexistente() {
        TransporteTropas v1 = new TransporteTropas("T-01", "Camión de tropas", 2020, 12000, EstadoOperativo.DISPONIBLE, 25);
        batallon.registrarVehiculo(v1);

        LOG.info("Iniciando prueba Vehiculo existente e inexistente");

        Vehiculo encontrado = batallon.buscarVehiculo("T-01");
        Vehiculo noEncontrado = batallon.buscarVehiculo("V-999");

        assertNotNull(encontrado);
        assertEquals("Camión de tropas", encontrado.getModelo());
        assertNull(noEncontrado);

        LOG.info("Finalizando prueba Vehiculo existente e inexistente");
    }

    /* Eliminar un vehiculo registrado */
    @Test
    @DisplayName("Prueba assertTrue/Null elimar vRegistrado en batallon")
    void testEliminarVehiculo() {
        TransporteTropas v1 = new TransporteTropas("T-01", "Camión de tropas", 2020, 12000, EstadoOperativo.DISPONIBLE, 25);
        batallon.registrarVehiculo(v1);

        LOG.info("Iniciando prueba Vehiculo registrado: ");

        boolean eliminado = batallon.eliminarVehiculo("T-01");

        assertTrue(eliminado);
        assertNull(batallon.buscarVehiculo("T-01"));

        LOG.info("Finalizando prueba Vehiculo registrado ");
    }

    @Test
    @DisplayName("Prueba asserts vehiculo misiones completadas no aparezca en el listado")
    void testVehiculoCon50MisionesNoAparece() {
        TransporteTropas transporte = new TransporteTropas("T-01", "Camión de tropas", 2020, 12000, EstadoOperativo.DISPONIBLE, 25);

        LOG.info("Iniciando prueba Vehiculo misiones completadas no aparece: ");

        assertTrue(batallon.registrarVehiculo(transporte));

        for (int i = 1; i <= 50; i++) {
            Mision mision = new Mision("M-" + i, LocalDate.of(2026, 10, 22), "Zona Norte");
            assertTrue(mision.agregarPersonal("Carlos López"));
            assertTrue(batallon.registrarMision(mision));
            assertTrue(batallon.asignarVehiculoMision("M-" + i, "T-01"));
            assertTrue(batallon.iniciarMision("M-" + i));
            assertTrue(batallon.completarMision("M-" + i));
        }

        assertEquals(50, transporte.contarMisionesCompletadas());

        List<Vehiculo> resultado = batallon.obtenerVehiculosConMasDe50Misiones();
        assertNotNull(resultado);

        boolean estaEnLista = false;
        for (Vehiculo v : resultado) {
            if (v.getId().equals("T-01")) {
                estaEnLista = true;
            }
        }

        assertFalse(estaEnLista);

        LOG.info("Finalizando prueba Vehiculo misiones completadas no aparece. ");
    }

    @Test
    @DisplayName("Prueba asserts con las misiones completadas")
    void testVehiculoCon51MisionesAparece() {
        TransporteTropas transporte = new TransporteTropas("T-01", "Camión de tropas", 2020, 12000, EstadoOperativo.DISPONIBLE, 25);

        LOG.info("Iniciando prueba Vehiculo misiones completadas: ");

        assertTrue(batallon.registrarVehiculo(transporte));

        for (int i = 1; i <= 51; i++) {
            Mision mision = new Mision("M-" + i, LocalDate.of(2026, 10, 22), "Zona Norte");
            assertTrue(mision.agregarPersonal("Carlos López"));
            assertTrue(batallon.registrarMision(mision));
            assertTrue(batallon.asignarVehiculoMision("M-" + i, "T-01"));
            assertTrue(batallon.iniciarMision("M-" + i));
            assertTrue(batallon.completarMision("M-" + i));
        }

        assertEquals(51, transporte.contarMisionesCompletadas());

       List<Vehiculo> resultado = batallon.obtenerVehiculosConMasDe50Misiones();
        assertNotNull(resultado);

        boolean estaEnLista = false;
        Vehiculo vehiculoEncontrado = null;
        for (Vehiculo v : resultado) {
            if (v.getId().equals("T-01")) {
                estaEnLista = true;
                vehiculoEncontrado = v;
            }
        }

        assertTrue(estaEnLista);
        assertNotNull(vehiculoEncontrado);
        assertEquals("T-01", vehiculoEncontrado.getId());
        assertEquals(51, vehiculoEncontrado.contarMisionesCompletadas());

        LOG.info("Finalizando prueba Vehiculo misiones completadas. ");
    }
}