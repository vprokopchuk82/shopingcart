package com.vprokopchuk.shoppingcart.utils;

import java.util.Objects;

public class IdGeneratorInitializer {
    private static IdGenerator idGenerator;

    private IdGeneratorInitializer() {
    }

    public static IdGenerator getInstance(){
        if (Objects.isNull(idGenerator)){
            synchronized (IdGeneratorInitializer.class) {
                idGenerator = new IdGenerator();
                return idGenerator;
            }
        }
        else return idGenerator;
    }


}
