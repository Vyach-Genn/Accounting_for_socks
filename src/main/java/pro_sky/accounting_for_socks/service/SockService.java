package pro_sky.accounting_for_socks.service;

import org.springframework.stereotype.Service;
import pro_sky.accounting_for_socks.model.entity.Sock;
import pro_sky.accounting_for_socks.repository.SockRepository;

@Service
public class SockService {

    private final SockRepository repository;

    private static final int MIN_COTTON_PART = 0;
    private static final int MAX_COTTON_PART = 100;
    private static final int MIN_QUANTITY = 1;

    public SockService(SockRepository repository) {
        this.repository = repository;
    }

    public void income(String color, Integer cottonPart, Integer quantity) {
        validateSockInput(color, cottonPart, quantity);

        Sock sock = repository.findByColorAndCottonPart(color, cottonPart)
                .orElseGet(() -> createNewSock(color, cottonPart));

        sock.setQuantity(sock.getQuantity() + quantity);
        repository.save(sock);
    }

    private void validateSockInput(String color, Integer cottonPart, Integer quantity) {
        validateColor(color);
        validateCottonPart(cottonPart);
        validateQuantity(quantity);
    }

    private void validateColor(String color) {
        if (color == null || color.isBlank()) {
            throw new IllegalArgumentException("Color must not be blank");
        }
    }

    private void validateCottonPart(Integer cottonPart) {
        if (cottonPart == null || cottonPart < MIN_COTTON_PART || cottonPart > MAX_COTTON_PART) {
            throw new IllegalArgumentException("Cotton part must be between 0 and 100");
        }
    }

    private void validateQuantity(Integer quantity) {
        if (quantity == null || quantity < MIN_QUANTITY) {
            throw new IllegalArgumentException("Quantity must be greater than 0");
        }
    }

    private Sock createNewSock(String color, Integer cottonPart) {
        Sock sock = new Sock();
        sock.setColor(color);
        sock.setCottonPart(cottonPart);
        sock.setQuantity(0);
        return sock;
    }


    public void outcome(String color, Integer cottonPart, Integer quantity) {
        Sock sock = findSockOrThrow(color, cottonPart);
        validateEnoughQuantity(sock, quantity);
        decreaseSockQuantity(sock, quantity);
        repository.save(sock);
    }

    private Sock findSockOrThrow(String color, Integer cottonPart) {
        return repository.findByColorAndCottonPart(color, cottonPart)
                .orElseThrow(() -> new IllegalArgumentException("Socks not found"));
    }

    private void validateEnoughQuantity(Sock sock, Integer requestedQuantity) {
        if (sock.getQuantity() < requestedQuantity) {
            throw new IllegalArgumentException("Not enough socks in stock");
        }
    }

    private void decreaseSockQuantity(Sock sock, Integer quantity) {
        sock.setQuantity(sock.getQuantity() - quantity);
    }


    public Integer getSocks(String color, String operationStr, Integer cottonPart) {
        Operation operation = Operation.fromString(operationStr);

        return switch (operation) {
            case MORE_THAN -> repository.sumQuantityByColorAndCottonPartMoreThan(color, cottonPart);
            case LESS_THAN -> repository.sumQuantityByColorAndCottonPartLessThan(color, cottonPart);
            case EQUAL -> repository.sumQuantityByColorAndCottonPartEqual(color, cottonPart);
        };
    }

}
