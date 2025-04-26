package pro_sky.accounting_for_socks.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pro_sky.accounting_for_socks.model.entity.Sock;
import pro_sky.accounting_for_socks.repository.SockRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class SockServiceTest {

    private SockRepository repository;
    private SockService service;

    @BeforeEach
    void setUp() {
        repository = mock(SockRepository.class);
        service = new SockService(repository);
    }

    @Test
    void income_NewSock_CreatesNewSock() {
        // given
        String color = "red";
        Integer cottonPart = 80;
        Integer quantity = 10;
        when(repository.findByColorAndCottonPart(color, cottonPart)).thenReturn(Optional.empty());

        // when
        service.income(color, cottonPart, quantity);

        // then
        verify(repository, times(1)).save(argThat(sock ->
                sock.getColor().equals(color) &&
                        sock.getCottonPart().equals(cottonPart) &&
                        sock.getQuantity().equals(quantity)
        ));
    }

    @Test
    void income_ExistingSock_IncrementsQuantity() {
        // given
        String color = "blue";
        Integer cottonPart = 60;
        Integer quantity = 5;
        Sock existingSock = new Sock(1L, color, cottonPart, 10);
        when(repository.findByColorAndCottonPart(color, cottonPart)).thenReturn(Optional.of(existingSock));

        // when
        service.income(color, cottonPart, quantity);

        // then
        verify(repository).save(argThat(sock ->
                sock.getQuantity().equals(15)
        ));
    }

    @Test
    void outcome_SuccessfullyReducesQuantity() {
        // given
        String color = "green";
        Integer cottonPart = 70;
        Integer quantity = 5;
        Sock existingSock = new Sock(1L, color, cottonPart, 10);
        when(repository.findByColorAndCottonPart(color, cottonPart)).thenReturn(Optional.of(existingSock));

        // when
        service.outcome(color, cottonPart, quantity);

        // then
        verify(repository).save(argThat(sock ->
                sock.getQuantity().equals(5)
        ));
    }

    @Test
    void outcome_NotEnoughSocks_ThrowsException() {
        // given
        String color = "yellow";
        Integer cottonPart = 50;
        Integer quantity = 20;
        Sock existingSock = new Sock(1L, color, cottonPart, 10);
        when(repository.findByColorAndCottonPart(color, cottonPart)).thenReturn(Optional.of(existingSock));

        // then
        assertThrows(IllegalArgumentException.class, () -> service.outcome(color, cottonPart, quantity));
    }

    @Test
    void outcome_SockNotFound_ThrowsException() {
        // given
        String color = "black";
        Integer cottonPart = 90;
        Integer quantity = 5;
        when(repository.findByColorAndCottonPart(color, cottonPart)).thenReturn(Optional.empty());

        // then
        assertThrows(IllegalArgumentException.class, () -> service.outcome(color, cottonPart, quantity));
    }

    @Test
    void getSocks_MoreThan_ReturnsQuantity() {
        // given
        String color = "white";
        String operation = "moreThan";
        Integer cottonPart = 70;
        when(repository.sumQuantityByColorAndCottonPartMoreThan(color, cottonPart)).thenReturn(30);

        // when
        Integer quantity = service.getSocks(color, operation, cottonPart);

        // then
        assertEquals(30, quantity);
    }

    @Test
    void getSocks_InvalidOperation_ThrowsException() {
        // given
        String color = "white";
        String operation = "invalid";
        Integer cottonPart = 70;

        // then
        assertThrows(IllegalArgumentException.class, () -> service.getSocks(color, operation, cottonPart));
    }
}
