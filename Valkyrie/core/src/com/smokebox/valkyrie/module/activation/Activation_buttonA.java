package com.smokebox.valkyrie.module.activation;

import com.smokebox.valkyrie.module.input.InputModule;

public class Activation_buttonA implements ActivationModule {

    InputModule ie;

    public Activation_buttonA(InputModule ie) {
        this.ie = ie;
    }

    @Override
    public boolean activate() {
        return ie.a();
    }
}
