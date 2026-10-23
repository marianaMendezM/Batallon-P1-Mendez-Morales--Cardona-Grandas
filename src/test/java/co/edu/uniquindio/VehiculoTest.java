package co.edu.uniquindio;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.logging.Logger;
import java.time.LocalDate;

class VehiculoTest {
    private static final Logger LOG = Logger.getLogger(Vehiculo.class.getName());

    @Test
    @DisplayName("Prueba assertTrue/Equals Vehiculo")
    void testCrearDiferentesTiposDeVehiculos() {
        TransporteTropas transporte = new TransporteTropas("T-01", "Camión de tropas", 2020, 12000.0, EstadoOperativo.DISPONIBLE, 25);

        LOG.info("Iniciando prueba assertsTrue - assertsEquals Vehiculo: ");

        assertEquals("T-01", transporte.getId());
        assertEquals("Camión de tropas", transporte.getModelo());
        assertEquals(25, transporte.getCapacidadSoldados());
        assertTrue(transporte.esValido());

        Blindado blindado = new Blindado("B-01", "Tanque Centauro", 2021, 8000.0, EstadoOperativo.DISPONIBLE, 4);
        assertEquals("B-01", blindado.getId());
        assertEquals(4, blindado.getNivelBlindaje());
        assertTrue(blindado.esValido());

        Apoyo apoyo = new Apoyo("A-01", "Ambulancia Ford", 2022, 5000.0, EstadoOperativo.DISPONIBLE, TipoFuncion.MEDICO);
        assertEquals("A-01", apoyo.getId());
        assertEquals(TipoFuncion.MEDICO, apoyo.getTipoFuncion());
        assertTrue(apoyo.esValido());

        LOG.info("Finalizando prueba assertsTrue - assertsEquals Vehiculo.");
    }

    @Test
    @DisplayName("Prueba assertTrue/Equals Vehiculo actualizar info:")
    void testActualizarInformacionVehiculo() {
        TransporteTropas transporte = new TransporteTropas("T-01", "Camión de tropas", 2020, 12000, EstadoOperativo.DISPONIBLE, 25);

        LOG.info("Iniciando prueba assertsTrue - assertsEquals Vehiculo: ");

        boolean kilometrajeActualizado = transporte.setKilometraje(15000);
        boolean estadoActualizado = transporte.setEstadoOperativo(EstadoOperativo.EN_MANTENIMIENTO);

        assertTrue(kilometrajeActualizado);
        assertTrue(estadoActualizado);
        assertEquals(15000, transporte.getKilometraje());
        assertEquals(EstadoOperativo.EN_MANTENIMIENTO, transporte.getEstadoOperativo());

        LOG.info("Finalizando prueba assertsTrue - assertsEquals Vehiculo actualizar info.");
    }

    @Test
    @DisplayName("Prueba assertTrue/Equals Vehiculo contar misiones completadas: ")
    void testContarUnicamenteMisionesCompletadas() {
        TransporteTropas transporte = new TransporteTropas("T-01", "Camión de tropas", 2020, 12000, EstadoOperativo.DISPONIBLE, 25);
        Batallon batallon = new Batallon("01", "Cacique", "03", "Calarcá", 3);

        LOG.info("Iniciando prueba assertsTrue - assertsEquals Vehiculo: ");

        assertTrue(batallon.registrarVehiculo(transporte));

        // Misión 1: Completada
        Mision m1 = new Mision("M-01", LocalDate.of(2026, 10, 22), "Zona A");
        m1.agregarPersonal("Pedro");
        assertTrue(batallon.registrarMision(m1));
        assertTrue(batallon.asignarVehiculoMision("M-01", "T-01"));
        assertTrue(batallon.iniciarMision("M-01"));
        assertTrue(batallon.completarMision("M-01"));

        // Misión 2: Completada
        Mision m2 = new Mision("M-02", LocalDate.of(2026, 10, 22), "Zona B");
        m2.agregarPersonal("Pedro");
        assertTrue(batallon.registrarMision(m2));
        assertTrue(batallon.asignarVehiculoMision("M-02", "T-01"));
        assertTrue(batallon.iniciarMision("M-02"));
        assertTrue(batallon.completarMision("M-02"));

        // Misión 3: Solo Planificada
        Mision m3 = new Mision("M-03", LocalDate.of(2026, 10, 22), "Zona C");
        m3.agregarPersonal("Pedro");
        assertTrue(batallon.registrarMision(m3));
        assertTrue(batallon.asignarVehiculoMision("M-03", "T-01"));

        assertEquals(2, transporte.contarMisionesCompletadas());

        LOG.info("Finalizando prueba assertsTrue - assertsEquals Vehiculo.");
    }
}