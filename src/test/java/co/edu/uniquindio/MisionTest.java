package co.edu.uniquindio;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.logging.Logger;
import java.time.LocalDate;


class MisionTest {
    private static final Logger LOG = Logger.getLogger(BatallonTest.class.getName());

    @Test
    @DisplayName("Prueba assertTrue/Equals Mision")

    void testRegistrarMisionConPersonalYVehiculos() {
        Batallon batallon = new Batallon("01", "Cacique", "03", "Calarcá", 3);

        LOG.info("Iniciando prueba assertsTrue - assertsEquals Mision: ");

        TransporteTropas v1 = new TransporteTropas("T-01", "Camión de tropas", 2020, 12000, EstadoOperativo.DISPONIBLE, 25);
        batallon.registrarVehiculo(v1);

        Mision mision = new Mision("M-01", LocalDate.of(2026, 10, 22), "Sector Norte");
        mision.agregarPersonal("Ana Pérez");
        mision.agregarPersonal("Luis Gómez");

        boolean registrada = batallon.registrarMision(mision);
        boolean vehiculoAsignado = batallon.asignarVehiculoMision("M-01", "T-01");

        assertTrue(registrada);
        assertTrue(vehiculoAsignado);
        assertEquals(2, mision.getPersonalAsignado().size());
        assertEquals(1, mision.getListaDetallesMision().size());
        assertEquals("T-01", mision.getListaDetallesMision().get(0).getVehiculo().getId());
        LOG.info("Finalizando prueba assertsTrue - assertsEquals Misio.");
    }

}