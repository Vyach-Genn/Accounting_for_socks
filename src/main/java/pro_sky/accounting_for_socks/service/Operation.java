package pro_sky.accounting_for_socks.service;

public enum Operation {
    MORE_THAN("moreThan"),
    LESS_THAN("lessThan"),
    EQUAL("equal");

    private final String value;

    Operation(String value) {
        this.value = value;
    }

    public static Operation fromString(String value) {
        for (Operation operation : Operation.values()) {
            if (operation.value.equalsIgnoreCase(value)) {
                return operation;
            }
        }
        throw new IllegalArgumentException("Invalid operation: " + value);
    }
}

